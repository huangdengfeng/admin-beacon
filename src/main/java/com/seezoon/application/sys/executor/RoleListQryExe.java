package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.RoleListQry;
import com.seezoon.application.sys.dto.clientobject.RoleCO;
import com.seezoon.domain.dao.mapper.SysRoleMapper;
import com.seezoon.domain.dao.po.SysRolePO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
public class RoleListQryExe {

    private final SysRoleMapper sysRoleMapper;

    public Response<List<RoleCO>> execute(@Valid @NotNull RoleListQry qry) {
        List<SysRolePO> pos =
                qry.isOnlyValid() ? sysRoleMapper.selectValid() : sysRoleMapper.selectAll();
        List<RoleCO> cos = pos.stream()
                .map(v -> new RoleCO(v.getId(), v.getCode(), v.getName(), v.getStatus()))
                .collect(Collectors.toList());
        return Response.success(cos);
    }
}
