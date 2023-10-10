package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.SysPermissionPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Integer id);

    int deleteByParentId(Integer parentId);

    int insert(SysPermissionPO row);

    int insertSelective(SysPermissionPO row);

    SysPermissionPO selectByPrimaryKey(Integer id);

    List<SysPermissionPO> selectAll();

    List<SysPermissionPO> selectValid();

    List<SysPermissionPO> selectByUid(Integer uid);

    SysPermissionPO selectByCode(String code);

    int updateByPrimaryKeySelective(SysPermissionPO row);

    int updateByPrimaryKey(SysPermissionPO row);
}