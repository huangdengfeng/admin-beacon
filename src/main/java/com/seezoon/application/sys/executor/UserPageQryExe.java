package com.seezoon.application.sys.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.seezoon.application.sys.dto.UserPageQry;
import com.seezoon.application.sys.dto.clientobject.UserCO;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.dao.po.SysUserPO.Condition;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
import com.seezoon.infrastructure.dto.Page;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.utils.DictUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 用户分页查询
 *
 * @author dfenghuang
 * @date 2023/9/14 23:38
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class UserPageQryExe {

    private final SysUserMapper sysUserMapper;

    public Response<Page<UserCO>> execute(@Valid @NotNull UserPageQry qry) {
        SysUserPO.Condition condition = new Condition();
        condition.setUid(qry.getUid());
        condition.setUserName(qry.getUserName());
        condition.setFuzzyName(qry.getFuzzyName());
        condition.setStatus(qry.getStatus());
        condition.setIncludeSysAdmin(false);
        PageHelper.startPage(qry.getPage(), qry.getPageSize(), qry.getOrderBy());
        PageSerializable<SysUserPO> page = new PageSerializable<>(sysUserMapper.selectByCondition(condition));
        List<UserCO> cos = new ArrayList<>();
        for (SysUserPO po : page.getList()) {
            UserCO co = new UserCO();
            co.setUid(po.getUid());
            co.setUserName(po.getUserName());
            co.setName(po.getName());
            co.setMobile(po.getMobile());
            co.setEmail(po.getEmail());
            co.setPhoto(po.getPhoto());
            co.setStatus(po.getStatus());
            co.setStatusName(DictUtils.getName(UserStatusVO.DICT_TYPE, po.getStatus()));
            co.setCreateTime(po.getCreateTime());
            co.setUpdateTime(po.getUpdateTime());
            co.setRemark(po.getRemark());
            cos.add(co);
        }
        return Response.success(new Page<>(page.getTotal(), cos));
    }
}
