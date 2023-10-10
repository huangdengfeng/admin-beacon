package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.ModifyMyPwdCmd;
import com.seezoon.domain.service.sys.ModifyUserService;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 修改我的密码
 *
 * @author dfenghuang
 * @date 2023/9/25 19:22
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class ModifyMyPwdCmdExe {

    private final ModifyUserService modifyUserService;

    public Response execute(@Valid @NotNull ModifyMyPwdCmd cmd) {
        Integer uid = SecurityContext.getUserId();
        modifyUserService.modifyPwd(uid, cmd.getOldPassword(), cmd.getNewPassword(), uid);
        return Response.success();
    }
}
