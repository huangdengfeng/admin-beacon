package com.seezoon.application.sys.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/26 14:13
 */
@Getter
@Setter
public class RoleDetailCO extends RoleCO {

    @Schema(title = "权限列表")
    private Set<Integer> permissionIds;

    public RoleDetailCO(Integer id, String code, String name, Byte status) {
        super(id, code, name, status);
        this.permissionIds = Collections.EMPTY_SET;
    }
}
