package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.ModifyUserPwdCmd;
import com.seezoon.domain.service.sys.ModifyUserService;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 修改用户密码
 *
 * @author dfenghuang
 * @date 2023/9/25 19:22
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class ModifyUserPwdCmdExe {

    private final ModifyUserService modifyUserService;

    public Response execute(@Valid @NotNull ModifyUserPwdCmd cmd) {
        if (SecurityContext.isSuperAdmin(cmd.getUid())) {
            return Response.error(ErrorCode.SYS_ADMIN_NOT_ALLOW_MODIFY.code(),
                    ErrorCode.SYS_ADMIN_NOT_ALLOW_MODIFY.msg());
        }
        Integer operator = SecurityContext.getUserId();
        modifyUserService.modifyPwd(cmd.getUid(), cmd.getPassword(), operator);
        return Response.success();
    }
}
