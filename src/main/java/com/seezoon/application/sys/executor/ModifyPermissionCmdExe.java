package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.ModifyPermissionCmd;
import com.seezoon.domain.service.sys.PermissionService;
import com.seezoon.domain.service.sys.valueobj.ModifyPermissionVO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 修改权限
 *
 * @author dfenghuang
 * @date 2023/9/25 19:44
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class ModifyPermissionCmdExe {

    private final PermissionService permissionService;

    public Response execute(@Valid @NotNull ModifyPermissionCmd cmd) {
        ModifyPermissionVO vo = new ModifyPermissionVO(cmd.getId(), cmd.getCode(), cmd.getName(), cmd.getParentId(),
                cmd.getStatus());
        permissionService.modify(vo, SecurityContext.getUserId());
        return Response.success();
    }
}
