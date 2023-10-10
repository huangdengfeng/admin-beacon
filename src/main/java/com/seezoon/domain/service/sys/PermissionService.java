package com.seezoon.domain.service.sys;

import static com.seezoon.infrastructure.utils.AffectedRowChecker.expectOneRow;

import com.seezoon.domain.dao.mapper.SysPermissionMapper;
import com.seezoon.domain.dao.mapper.SysRolePermissionMapper;
import com.seezoon.domain.dao.po.SysPermissionPO;
import com.seezoon.domain.service.sys.valueobj.AddPermissionVO;
import com.seezoon.domain.service.sys.valueobj.ModifyPermissionVO;
import com.seezoon.infrastructure.constants.Constants;
import com.seezoon.infrastructure.constants.DbRecordStatus;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 权限服务
 *
 * @author dfenghuang
 * @date 2023/9/23 08:42
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
@Validated
public class PermissionService {

    private final SysPermissionMapper sysPermissionMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 添加
     *
     * @param vo
     * @param operator
     * @return not null 主键
     */
    public Integer add(@Valid @NotNull AddPermissionVO vo, @NotNull Integer operator) {
        SysPermissionPO existsPO = sysPermissionMapper.selectByCode(vo.getCode());
        if (null != existsPO) {
            throw ExceptionFactory.bizException(ErrorCode.PERMISSION_CODE_EXISTS);
        }
        Integer parentId = vo.getParentId();
        checkParent(parentId);
        SysPermissionPO poToSave = new SysPermissionPO();
        poToSave.setCode(vo.getCode());
        poToSave.setName(vo.getName());
        poToSave.setParentId(parentId);
        poToSave.setParentIds(this.createParentIds(parentId));
        poToSave.setStatus(DbRecordStatus.VALID);
        LocalDateTime now = LocalDateTime.now();
        poToSave.setCreateUser(operator);
        poToSave.setCreateTime(now);
        poToSave.setUpdateUser(operator);
        poToSave.setUpdateTime(now);
        int affectedRows = sysPermissionMapper.insert(poToSave);
        expectOneRow(affectedRows);
        return Objects.requireNonNull(poToSave.getId());
    }

    public void modify(@Valid @NotNull ModifyPermissionVO vo, @NotNull Integer operator) {
        DbRecordStatus.check(vo.getStatus());
        Integer id = vo.getId();
        SysPermissionPO keyPO = sysPermissionMapper.selectByPrimaryKey(id);
        if (null == keyPO) {
            throw ExceptionFactory.bizException(ErrorCode.RECORD_NOT_EXISTS);
        }
        SysPermissionPO codePO = sysPermissionMapper.selectByCode(vo.getCode());
        if (null != codePO && !Objects.equals(codePO.getId(), vo.getId())) {
            throw ExceptionFactory.bizException(ErrorCode.PERMISSION_CODE_EXISTS);
        }
        checkParent(vo.getParentId());
        keyPO.setCode(vo.getCode());
        keyPO.setName(vo.getName());
        keyPO.setParentId(vo.getParentId());
        keyPO.setParentIds(this.createParentIds(vo.getParentId()));
        keyPO.setStatus(vo.getStatus());
        keyPO.setUpdateUser(operator);
        keyPO.setUpdateTime(LocalDateTime.now());
        int affectedRow = sysPermissionMapper.updateByPrimaryKey(keyPO);
        expectOneRow(affectedRow);
    }

    public void delete(@NotNull Integer permissionId) {
        SysPermissionPO po = sysPermissionMapper.selectByPrimaryKey(permissionId);
        if (null == po) {
            throw ExceptionFactory.bizException(ErrorCode.RECORD_NOT_EXISTS);
        }
        int affectedRow = sysPermissionMapper.deleteByPrimaryKey(permissionId);
        expectOneRow(affectedRow);
        // 删除子节点
        affectedRow = sysPermissionMapper.deleteByParentId(permissionId);
        log.info("delete child  count:{},parent permission id [{}]", affectedRow, permissionId);
        // 删除权限和角色关系表
        affectedRow = sysRolePermissionMapper.deleteByPermissionId(permissionId);
        log.info("delete permission id [{}] role count:{}", permissionId, affectedRow);
    }

    private void checkParent(Integer parentId) {
        if (null == parentId) {
            return;
        }
        SysPermissionPO po = sysPermissionMapper.selectByPrimaryKey(parentId);
        if (null == po) {
            throw ExceptionFactory.bizException(ErrorCode.PERMISSION_PARENT_NOT_EXISTS);
        }
    }

    public String createParentIds(Integer parentId) {
        if (null == parentId) {
            return null;
        }
        SysPermissionPO parent = Objects.requireNonNull(this.sysPermissionMapper.selectByPrimaryKey(parentId));
        if (StringUtils.isEmpty(parent.getParentIds())) {
            return String.valueOf(parentId);
        }
        return StringUtils.joinWith(Constants.COMMA, parent.getParentIds(), parentId);
    }
}
