package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.AddUserCmd;
import com.seezoon.domain.service.sys.AddUserService;
import com.seezoon.domain.service.sys.valueobj.AddUserVO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 添加系统用户
 *
 * @author huangdengfeng
 * @date 2023/8/27 10:16
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class AddUserCmdExe {

    private final AddUserService addUserService;

    public Response execute(@Valid @NotNull AddUserCmd cmd) {
        Integer createUser = SecurityContext.getUserId();
        AddUserVO vo = new AddUserVO(cmd.getUserName(), cmd.getName());
        vo.setPassword(cmd.getPassword());
        vo.setMobile(cmd.getMobile());
        vo.setEmail(cmd.getEmail());
        vo.setPhoto(cmd.getPhoto());
        vo.setRemark(cmd.getRemark());
        vo.setRoleIds(cmd.getRoleIds());
        addUserService.add(vo, createUser);
        return Response.success();
    }
}
