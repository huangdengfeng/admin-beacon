package com.seezoon.domain.service.sys;

import static com.seezoon.infrastructure.utils.AffectedRowChecker.expectRow;

import com.seezoon.domain.dao.mapper.SysRoleMapper;
import com.seezoon.domain.dao.mapper.SysUserRoleMapper;
import com.seezoon.domain.dao.po.SysUserRolePO;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 给用户添加角色
 *
 * @author dfenghuang
 * @date 2023/9/24 08:38
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
@Validated
public class AddUserRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 保存用户角色
     *
     * <p>
     * 先删除用户角色后重新保存
     * </p>
     *
     * @param roleIds
     * @param uid
     * @param operator
     * @return 影响函数
     */
    public int saveRoles(@NotEmpty Set<Integer> roleIds, @NotNull Integer uid, @NotNull Integer operator) {
        int deleteRows = sysUserRoleMapper.deleteByUserId(uid);
        log.info("delete uid [{}] role count:{}", uid, deleteRows);
        List<Integer> allRoleIds = sysRoleMapper.selectAll().stream().map(v -> v.getId()).collect(Collectors.toList());
        if (!allRoleIds.containsAll(roleIds)) {
            throw ExceptionFactory.bizException(ErrorCode.ROLE_LIST_ERROR);
        }
        LocalDateTime now = LocalDateTime.now();
        List<SysUserRolePO> toSaveList = roleIds.stream().map(roleId -> {
            SysUserRolePO po = new SysUserRolePO();
            po.setUid(uid);
            po.setRoleId(roleId);
            po.setCreateUser(operator);
            po.setCreateTime(now);
            return po;
        }).collect(Collectors.toList());
        int affectedRows = sysUserRoleMapper.insertBatch(toSaveList);
        expectRow(toSaveList.size(), affectedRows);
        return affectedRows;
    }
}
