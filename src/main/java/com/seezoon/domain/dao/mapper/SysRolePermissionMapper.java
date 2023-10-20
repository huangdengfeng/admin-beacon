package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.SysRolePermissionPO;
import com.seezoon.domain.dao.po.SysRolePermissionPO.SysRolePermissionKey;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(SysRolePermissionKey key);

    int deleteByPermissionId(Integer permissionId);

    int deleteByRoleId(Integer roleId);

    int insert(SysRolePermissionPO row);

    int insertBatch(List<SysRolePermissionPO> rows);

    int insertSelective(SysRolePermissionPO row);

    SysRolePermissionPO selectByPrimaryKey(SysRolePermissionKey key);

    List<SysRolePermissionPO> selectByRoleId(Integer roleId);

    int updateByPrimaryKeySelective(SysRolePermissionPO row);

    int updateByPrimaryKey(SysRolePermissionPO row);
}