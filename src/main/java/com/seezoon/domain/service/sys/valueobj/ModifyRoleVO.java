package com.seezoon.domain.service.sys.valueobj;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/23 13:10
 */
@Getter
@Setter
public class ModifyRoleVO {

    @NotNull
    private Integer id;
    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    @NotNull
    private Byte status;
    @NotNull
    private Set<Integer> permissionIds;

    public ModifyRoleVO(Integer id, String code, String name, Byte status, Set<Integer> permissionIds) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.permissionIds = permissionIds;
    }
}
