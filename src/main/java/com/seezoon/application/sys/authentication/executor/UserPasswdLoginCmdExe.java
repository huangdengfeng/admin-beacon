package com.seezoon.application.sys.authentication.executor;

import com.seezoon.application.sys.authentication.dto.UserPasswdLoginCmd;
import com.seezoon.application.sys.authentication.dto.clientobject.AuthorizationTokenCO;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.LoginTokenService;
import com.seezoon.domain.service.sys.authentication.UserPasswdVerifyService;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 账号密码登录
 *
 * @author huangdengfeng
 * @date 2022/10/12 12:50
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class UserPasswdLoginCmdExe {

    private final LoginTokenService loginTokenService;
    private final UserPasswdVerifyService userPasswdVerifyService;
    private final SysUserMapper sysUserMapper;

    public Response<AuthorizationTokenCO> execute(@NotNull @Valid UserPasswdLoginCmd cmd) {
        // 验密
        boolean verified = userPasswdVerifyService.verify(cmd.getUsername(), cmd.getPassword());
        if (!verified) {
            return Response.error(ErrorCode.USER_PASSWD_WRONG.code(), ErrorCode.USER_PASSWD_WRONG.msg());
        }
        SysUserPO sysUserPO = sysUserMapper.selectByUserName(cmd.getUsername());
        Byte userStatus = sysUserPO.getStatus();

        if (UserStatusVO.inValid(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_INVALID.code(), ErrorCode.USER_STATUS_INVALID.msg());
        }
        if (UserStatusVO.isLocked(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_LOCKED.code(), ErrorCode.USER_STATUS_LOCKED.msg());
        }
        String accessToken = loginTokenService.create(sysUserPO.getUid(), sysUserPO.getSecretKey());
        return Response.success(new AuthorizationTokenCO(accessToken));
    }
}
