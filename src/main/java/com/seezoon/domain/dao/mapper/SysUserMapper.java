package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.SysUserPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    int deleteByPrimaryKey(Integer uid);

    int insert(SysUserPO row);

    List<SysUserPO> selectByCondition(SysUserPO.Condition condition);

    SysUserPO selectByPrimaryKey(Integer uid);

    SysUserPO selectByUserName(String userName);

    int updateByPrimaryKeySelective(SysUserPO row);

    int updateByPrimaryKey(SysUserPO row);
}