package com.seezoon.domain.service.sys;

import static com.seezoon.infrastructure.utils.AffectedRowChecker.expectOneRow;
import static com.seezoon.infrastructure.utils.AffectedRowChecker.expectRow;

import com.seezoon.domain.dao.mapper.SysPermissionMapper;
import com.seezoon.domain.dao.mapper.SysRoleMapper;
import com.seezoon.domain.dao.mapper.SysRolePermissionMapper;
import com.seezoon.domain.dao.mapper.SysUserRoleMapper;
import com.seezoon.domain.dao.po.SysRolePO;
import com.seezoon.domain.dao.po.SysRolePermissionPO;
import com.seezoon.domain.service.sys.valueobj.AddRoleVO;
import com.seezoon.domain.service.sys.valueobj.ModifyRoleVO;
import com.seezoon.infrastructure.constants.DbRecordStatus;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

/**
 * 角色服务
 *
 * @author dfenghuang
 * @date 2023/9/23 13:01
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
@Validated
public class RoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysPermissionMapper sysPermissionMapper;

    public Integer add(@Valid @NotNull AddRoleVO vo, @NotNull Integer operator) {
        String code = vo.getCode();
        SysRolePO existsPO = sysRoleMapper.selectByCode(code);
        if (null != existsPO) {
            throw ExceptionFactory.bizException(ErrorCode.ROLE_CODE_EXISTS);
        }
        SysRolePO sysRolePO = new SysRolePO();
        sysRolePO.setCode(code);
        sysRolePO.setName(vo.getName());
        sysRolePO.setStatus(DbRecordStatus.VALID);
        LocalDateTime now = LocalDateTime.now();
        sysRolePO.setCreateUser(operator);
        sysRolePO.setCreateTime(now);
        sysRolePO.setUpdateUser(operator);
        sysRolePO.setUpdateTime(now);
        int affectedRows = sysRoleMapper.insert(sysRolePO);
        expectOneRow(affectedRows);
        Integer roleId = Objects.requireNonNull(sysRolePO.getId());
        // 处理权限
        savePermission(vo.getPermissionIds(), roleId, operator);
        return roleId;
    }

    public void modify(@Valid @NotNull ModifyRoleVO vo, @NotNull Integer operator) {
        DbRecordStatus.check(vo.getStatus());
        SysRolePO existsPO = sysRoleMapper.selectByCode(vo.getCode());
        if (null != existsPO && !Objects.equals(existsPO.getId(), vo.getId())) {
            throw ExceptionFactory.bizException(ErrorCode.ROLE_CODE_EXISTS);
        }
        SysRolePO po = sysRoleMapper.selectByPrimaryKey(vo.getId());
        if (null == po) {
            throw ExceptionFactory.bizException(ErrorCode.RECORD_NOT_EXISTS);
        }
        po.setCode(vo.getCode());
        po.setName(vo.getName());
        po.setStatus(vo.getStatus());
        po.setUpdateUser(operator);
        LocalDateTime now = LocalDateTime.now();
        po.setUpdateTime(now);
        int affectedRows = sysRoleMapper.updateByPrimaryKey(po);
        expectOneRow(affectedRows);
        // 处理权限
        // 先删除后添加
        affectedRows = sysRolePermissionMapper.deleteByRoleId(vo.getId());
        log.info("delete role id [{}] , delete permission count:{}", vo.getId(), affectedRows);
        if (CollectionUtils.isEmpty(vo.getPermissionIds())) {
            return;
        }
        savePermission(vo.getPermissionIds(), vo.getId(), operator);
    }

    public void delete(@NotNull Integer roleId) {
        SysRolePO po = sysRoleMapper.selectByPrimaryKey(roleId);
        if (null == po) {
            throw ExceptionFactory.bizException(ErrorCode.RECORD_NOT_EXISTS);
        }
        int affectedRows = sysRoleMapper.deleteByPrimaryKey(roleId);
        expectOneRow(affectedRows);
        // 删除关联用户
        affectedRows = sysUserRoleMapper.deleteByRoleId(roleId);
        log.info("delete role id [{}] user count:{}", roleId, affectedRows);
        // 删除关联权限
        affectedRows = sysRolePermissionMapper.deleteByRoleId(roleId);
        log.info("delete role id [{}] permission count:{}", roleId, affectedRows);
    }

    private void savePermission(Set<Integer> permissionIds, Integer roleId, Integer operator) {
        if (null == permissionIds) {
            return;
        }
        // 检查权限是否合法
        List<Integer> allPermissionIds = sysPermissionMapper.selectAll().stream().map(v -> v.getId())
                .collect(Collectors.toList());
        if (!allPermissionIds.containsAll(permissionIds)) {
            throw ExceptionFactory.bizException(ErrorCode.PERMISSION_LIST_ERROR);
        }
        LocalDateTime now = LocalDateTime.now();
        List<SysRolePermissionPO> toSaveList = permissionIds.stream().map(pid -> {
            SysRolePermissionPO po = new SysRolePermissionPO();
            po.setRoleId(roleId);
            po.setPermissionId(pid);
            po.setCreateUser(operator);
            po.setCreateTime(now);
            return po;
        }).collect(Collectors.toList());
        int affectedRows = sysRolePermissionMapper.insertBatch(toSaveList);
        expectRow(toSaveList.size(), affectedRows);
        log.info("add role id [{}] , permission count:{}", roleId, affectedRows);
    }
}
