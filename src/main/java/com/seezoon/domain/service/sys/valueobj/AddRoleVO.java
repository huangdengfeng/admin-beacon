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
public class AddRoleVO {

    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    @NotNull
    private Set<Integer> permissionIds;

    public AddRoleVO(String code, String name, Set<Integer> permissionIds) {
        this.code = code;
        this.name = name;
        this.permissionIds = permissionIds;
    }
}
