package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.AddRoleCmd;
import com.seezoon.domain.service.sys.RoleService;
import com.seezoon.domain.service.sys.valueobj.AddRoleVO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author dfenghuang
 * @date 2023/9/25 19:44
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class AddRoleCmdExe {

    private final RoleService roleService;

    public Response execute(@Valid @NotNull AddRoleCmd cmd) {
        AddRoleVO vo = new AddRoleVO(cmd.getCode(), cmd.getName(), cmd.getPermissionIds());
        roleService.add(vo, SecurityContext.getUserId());
        return Response.success();
    }
}
