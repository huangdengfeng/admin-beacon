package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/25 19:42
 */
@Getter
@Setter
public class ModifyPermissionCmd {

    @Schema(title = "主键")
    @NotNull
    private Integer id;

    @Schema(title = "权限编码", description = "唯一")
    @NotEmpty
    private String code;

    @Schema(title = "权限名称")
    @NotEmpty
    private String name;

    @Schema(title = "父节点", description = "无父节点传 null")
    private Integer parentId;

    @Schema(title = "状态", description = "字典 record-status")
    @NotNull
    private Byte status;
}
