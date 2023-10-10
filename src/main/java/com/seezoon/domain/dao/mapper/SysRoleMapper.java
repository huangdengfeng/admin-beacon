package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.SysRolePO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePO row);

    int insertSelective(SysRolePO row);

    SysRolePO selectByPrimaryKey(Integer id);

    List<SysRolePO> selectAll();

    List<SysRolePO> selectValid();

    List<SysRolePO> selectByUid(Integer uid);

    SysRolePO selectByCode(String code);

    int updateByPrimaryKeySelective(SysRolePO row);

    int updateByPrimaryKey(SysRolePO row);
}