package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.SysUserRolePO;
import com.seezoon.domain.dao.po.SysUserRolePO.SysUserRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(SysUserRoleKey key);

    int deleteByRoleId(Integer roleId);

    int deleteByUserId(Integer userId);

    int insert(SysUserRolePO row);

    int insertBatch(List<SysUserRolePO> rows);

    int insertSelective(SysUserRolePO row);

    SysUserRolePO selectByPrimaryKey(SysUserRoleKey key);

    List<Integer> selectByUserId(Integer uid);

    int updateByPrimaryKeySelective(SysUserRolePO row);

    int updateByPrimaryKey(SysUserRolePO row);
}