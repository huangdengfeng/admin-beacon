package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.DeleteRoleCmd;
import com.seezoon.domain.service.sys.RoleService;
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
public class DeleteRoleCmdExe {

    private final RoleService roleService;

    public Response execute(@Valid @NotNull DeleteRoleCmd cmd) {
        roleService.delete(cmd.getId());
        return Response.success();
    }
}
