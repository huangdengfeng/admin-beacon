package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.RoleDetailQry;
import com.seezoon.application.sys.dto.clientobject.RoleDetailCO;
import com.seezoon.domain.dao.mapper.SysRoleMapper;
import com.seezoon.domain.dao.mapper.SysRolePermissionMapper;
import com.seezoon.domain.dao.po.SysRolePO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 权限查询
 *
 * @author huangdengfeng
 * @date 2023/8/27 10:16
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
@Transactional(readOnly = true)
public class RoleDetailQryExe {

    private final SysRoleMapper sysRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;

    public Response<RoleDetailCO> execute(@Valid @NotNull RoleDetailQry qry) {
        SysRolePO po = sysRoleMapper.selectByPrimaryKey(qry.getRoleId());
        if (null == po) {
            Response.error(ErrorCode.RECORD_NOT_EXISTS.code(), ErrorCode.RECORD_NOT_EXISTS.msg());
        }
        RoleDetailCO co = new RoleDetailCO(po.getId(), po.getCode(), po.getName(), po.getStatus());
        // 查询角色下权限列表
        Set<Integer> permissionIds = sysRolePermissionMapper.selectByRoleId(qry.getRoleId()).stream()
                .map(v -> v.getPermissionId()).collect(Collectors.toSet());
        co.setPermissionIds(permissionIds);
        return Response.success(co);
    }
}
