package com.seezoon.application.sys.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/25 23:19
 */
@Getter
@Setter
public class PermissionCO {

    @Schema(title = "ID")
    private Integer id;
    @Schema(title = "权限编码")
    private String code;
    @Schema(title = "权限名称")
    private String name;
    @Schema(title = "父节点", description = "根节点为null")
    private Integer parentId;
    @Schema(title = "状态", description = "字典record-status")
    private Byte status;
    @Schema(title = "子节点")
    private List<PermissionCO> children = Collections.EMPTY_LIST;

    public PermissionCO(Integer id, String code, String name, Integer parentId, Byte status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.status = status;
    }
}
