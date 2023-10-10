package com.seezoon.application.sys.dto;

import com.seezoon.infrastructure.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPageQry extends PageQuery {

    @Schema(title = "用户ID", description = "可以查到唯一记录")
    private Integer uid;
    @Schema(title = "用户名")
    private String userName;
    @Schema(title = "姓名", description = "模糊查询")
    private String fuzzyName;
    @Schema(title = "状态")
    private Byte status;

    @Override
    public List<String> allowedOrderFields() {
        return Arrays.asList("updateTime", "createTime");
    }
}
