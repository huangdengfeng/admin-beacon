package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.authentication.context.SecurityContext;
import com.seezoon.application.sys.dto.clientobject.MyInfoCO;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.UserPermissionService;
import com.seezoon.infrastructure.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 个人信息
 *
 * @author dfenghuang
 * @date 2023/9/25 17:14
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class MyInfoQryExe {

    private final UserPermissionService userPermissionService;
    private final SysUserMapper sysUserMapper;

    public Response<MyInfoCO> execute() {
        Integer userId = SecurityContext.getUserId();
        SysUserPO po = sysUserMapper.selectByPrimaryKey(userId);
        MyInfoCO co = new MyInfoCO();
        co.setUserName(po.getUserName());
        co.setName(po.getName());
        co.setMobile(po.getMobile());
        co.setEmail(po.getEmail());
        co.setPhoto(po.getPhoto());
        co.setRoles(userPermissionService.getUserRoles(userId));
        co.setRoleNames(userPermissionService.getUserRoleNames(userId));
        co.setPermissions(userPermissionService.getPermissions(userId));
        return Response.success(co);
    }
}
