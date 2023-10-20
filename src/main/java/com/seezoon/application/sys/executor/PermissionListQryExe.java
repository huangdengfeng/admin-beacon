package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.PermissionListQry;
import com.seezoon.application.sys.dto.clientobject.PermissionCO;
import com.seezoon.domain.dao.mapper.SysPermissionMapper;
import com.seezoon.domain.dao.po.SysPermissionPO;
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
public class PermissionListQryExe {

    private final SysPermissionMapper sysPermissionMapper;

    public Response<List<PermissionCO>> execute(@Valid @NotNull PermissionListQry qry) {
        List<SysPermissionPO> pos =
                qry.isOnlyValid() ? sysPermissionMapper.selectValid() : sysPermissionMapper.selectAll();

        List<PermissionCO> cos = pos.stream().map(v -> new PermissionCO(v.getId(), v.getCode(), v.getName(),
                v.getParentId(), v.getStatus())).collect(Collectors.toList());
        List<PermissionCO> tree = buildTree(cos);
        return Response.success(tree);
    }

    public List<PermissionCO> buildTree(List<PermissionCO> permissions) {
        List<PermissionCO> roots = permissions.stream()
                .filter(permission -> permission.getParentId() == null)
                .collect(Collectors.toList());

        roots.forEach(root -> buildTree(root, permissions));

        return roots;
    }

    private void buildTree(PermissionCO parent, List<PermissionCO> permissions) {
        List<PermissionCO> children = permissions.stream()
                .filter(permission -> parent.getId().equals(permission.getParentId()))
                .collect(Collectors.toList());

        parent.setChildren(children);

        children.forEach(child -> buildTree(child, permissions));
    }


}
