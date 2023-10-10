package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.AddPermissionCmd;
import com.seezoon.domain.service.sys.PermissionService;
import com.seezoon.domain.service.sys.valueobj.AddPermissionVO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 添加权限
 *
 * @author dfenghuang
 * @date 2023/9/25 19:44
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class AddPermissionCmdExe {

    private final PermissionService permissionService;

    public Response execute(@Valid @NotNull AddPermissionCmd cmd) {
        AddPermissionVO vo = new AddPermissionVO(cmd.getCode(), cmd.getName(), cmd.getParentId());
        permissionService.add(vo, SecurityContext.getUserId());
        return Response.success();
    }
}
