package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.DeletePermissionCmd;
import com.seezoon.domain.service.sys.PermissionService;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 删除权限
 *
 * @author dfenghuang
 * @date 2023/9/25 19:44
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class DeletePermissionCmdExe {

    private final PermissionService permissionService;

    public Response execute(@Valid @NotNull DeletePermissionCmd cmd) {
        permissionService.delete(cmd.getId());
        return Response.success();
    }
}
