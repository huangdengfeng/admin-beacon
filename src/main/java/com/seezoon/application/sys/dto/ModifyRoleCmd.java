package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/25 19:42
 */
@Getter
@Setter
public class ModifyRoleCmd {

    @Schema(title = "主键")
    @NotNull
    private Integer id;
    @Schema(title = "角色编码", description = "唯一")
    @NotEmpty
    private String code;
    @Schema(title = "角色名称")
    @NotEmpty
    private String name;
    @Schema(title = "状态", description = "字典recore-status")
    @NotNull
    private Byte status;

    @NotNull
    @Schema(title = "权限列表")
    private Set<Integer> permissionIds;
}
