package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.ModifyUserCmd;
import com.seezoon.domain.service.sys.ModifyUserService;
import com.seezoon.domain.service.sys.valueobj.ModifyUserVO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 修改用户信息
 *
 * @author dfenghuang
 * @date 2023/9/14 22:43
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class ModifyUserCmdExe {

    private final ModifyUserService modifyUserService;

    public Response execute(@Valid @NotNull ModifyUserCmd cmd) {
        Integer userId = SecurityContext.getUserId();
        if (SecurityContext.isSuperAdmin(userId)) {
            return Response.error(ErrorCode.SYS_ADMIN_NOT_ALLOW_MODIFY.code(),
                    ErrorCode.SYS_ADMIN_NOT_ALLOW_MODIFY.msg());
        }
        ModifyUserVO vo = new ModifyUserVO(cmd.getUid(), cmd.getUserName(), cmd.getName(), cmd.getStatus());
        vo.setMobile(cmd.getMobile());
        vo.setEmail(cmd.getEmail());
        vo.setPhoto(cmd.getPhoto());
        vo.setRemark(cmd.getRemark());
        vo.setRoleIds(cmd.getRoleIds());
        modifyUserService.modify(vo, userId);
        return Response.success();
    }
}
