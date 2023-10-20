package com.seezoon.domain.service.sys.authentication;

import com.seezoon.domain.dao.mapper.SysPermissionMapper;
import com.seezoon.domain.dao.mapper.SysRoleMapper;
import com.seezoon.domain.dao.po.SysPermissionPO;
import com.seezoon.domain.dao.po.SysRolePO;
import com.seezoon.infrastructure.constants.Constants;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 用户权限
 *
 * @author dfenghuang
 * @date 2023/9/29 00:32
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
public class UserPermissionService {

    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    public List<String> getUserRoles(@NotNull Integer uid) {
        if (isAdmin(uid)) {
            return sysRoleMapper.selectValid().stream().map(v -> v.getCode()).collect(
                    Collectors.toList());
        }
        List<SysRolePO> pos = sysRoleMapper.selectByUid(uid);
        return pos.stream().map(v -> v.getCode()).collect(Collectors.toList());
    }

    public List<String> getUserRoleNames(@NotNull Integer uid) {
        if (isAdmin(uid)) {
            return sysRoleMapper.selectValid().stream().map(v -> v.getName()).collect(
                    Collectors.toList());
        }
        List<SysRolePO> pos = sysRoleMapper.selectByUid(uid);
        return pos.stream().map(v -> v.getName()).collect(Collectors.toList());
    }

    public List<String> getPermissions(@NotNull Integer uid) {
        if (isAdmin(uid)) {
            return sysPermissionMapper.selectValid().stream().map(v -> v.getCode()).collect(
                    Collectors.toList());
        }
        List<SysPermissionPO> pos = sysPermissionMapper.selectByUid(uid);
        return pos.stream().map(v -> v.getCode()).collect(Collectors.toList());
    }

    private boolean isAdmin(Integer uid) {
        return Constants.SUPER_ADMIN_USER_ID == uid;
    }

}
