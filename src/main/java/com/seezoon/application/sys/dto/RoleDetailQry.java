package com.seezoon.application.sys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/26 09:40
 */
@Getter
@Setter
public class RoleDetailQry {

    @NotNull
    private Integer roleId;

    public RoleDetailQry(Integer roleId) {
        this.roleId = roleId;
    }
}
