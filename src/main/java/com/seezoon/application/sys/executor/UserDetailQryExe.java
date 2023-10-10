package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.UserDetailQry;
import com.seezoon.application.sys.dto.clientobject.UserDetailCO;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.mapper.SysUserRoleMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author dfenghuang
 * @date 2023/9/24 17:19
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class UserDetailQryExe {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    public Response<UserDetailCO> execute(@Valid @NotNull UserDetailQry qry) {
        SysUserPO po = sysUserMapper.selectByPrimaryKey(qry.getUid());
        UserDetailCO co = new UserDetailCO();
        co.setUid(po.getUid());
        co.setUserName(po.getUserName());
        co.setName(po.getName());
        co.setMobile(po.getMobile());
        co.setEmail(po.getEmail());
        co.setPhoto(po.getPhoto());
        co.setStatus(po.getStatus());
        co.setCreateTime(po.getCreateTime());
        co.setUpdateTime(po.getUpdateTime());
        co.setRemark(po.getRemark());

        List<Integer> roleIds = sysUserRoleMapper.selectByUserId(po.getUid());
        co.setRoleIds(roleIds);
        return Response.success(co);
    }
}
