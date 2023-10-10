package com.seezoon.infrastructure.dto;

import com.seezoon.infrastructure.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public abstract class PageQuery {


    public static final int DEFAULT_PAGE_SIZE_LIMIT = 1000;

    /**
     * 页码
     */
    @Schema(title = "页码", description = "默认为1")
    private int page = 1;
    /**
     * 每页条数
     */
    @Schema(title = "每页条数", description = "默认10")
    private int pageSize = 10;
    /**
     * 排序字段，可空
     */
    @Schema(title = "排序字段", description = "驼峰转下划线,asc | desc ，不支持大写")
    private String orderBy;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > this.pageSizeLimit()) {
            throw new IllegalArgumentException("page size limit:" + this.pageSizeLimit() + ",current is " + pageSize);
        }
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        if (!isValidOrderBy(orderBy)) {
            throw new IllegalArgumentException(
                    "order field error , must in" + StringUtils.join(allowedOrderFields()));
        }
        this.orderBy = StringUtils.replacePattern(orderBy, "([A-Z])", "_$1").toLowerCase();
    }

    /**
     * 每页条数限制
     *
     * @return
     */
    public int pageSizeLimit() {
        return DEFAULT_PAGE_SIZE_LIMIT;
    }

    public boolean isValidOrderBy(String orderBy) {
        if (StringUtils.isBlank(orderBy)) {
            return true;
        }
        // 定义一个白名单，存储允许的字段
        List<String> allowedFields = this.allowedOrderFields();
        // 分割orderBy字符串，获取所有的字段
        String[] fields = orderBy.split(Constants.COMMA);
        // 检查每一个字段是否在白名单中
        for (String field : fields) {
            // 去除可能存在的排序方式（ASC或DESC）
            String trimmedField = field.trim().split(" ")[0];

            // 如果字段不在白名单中，返回false
            if (!allowedFields.contains(trimmedField)) {
                return false;
            }
        }
        // 如果所有字段都在白名单中，返回true
        return true;
    }

    public List<String> allowedOrderFields() {
        return Collections.emptyList();
    }

}
