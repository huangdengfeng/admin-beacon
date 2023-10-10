package com.seezoon.domain.service.sys;

import static com.seezoon.infrastructure.utils.AffectedRowChecker.expectOneRow;

import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.support.PasswordEncoder;
import com.seezoon.domain.service.sys.support.UserSecretKeyGenerator;
import com.seezoon.domain.service.sys.valueobj.AddUserVO;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

/**
 * 添加用户
 *
 * @author huangdengfeng
 * @date 2023/8/26 18:27
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
@Validated
public class AddUserService {

    private final AddUserRoleService addUserRoleService;

    private final SysUserMapper sysUserMapper;

    /**
     * 添加用户
     *
     * @param vo
     * @param operator
     * @return not null uid
     */
    public Integer add(@Valid @NotNull AddUserVO vo, @NotNull Integer operator) {
        // 前置检查
        SysUserPO exists = sysUserMapper.selectByUserName(vo.getUserName());
        if (null != exists) {
            throw ExceptionFactory.bizException(ErrorCode.SYS_USER_NAME_EXISTS);
        }
        SysUserPO po = new SysUserPO();
        po.setUserName(vo.getUserName());
        // 加密
        if (StringUtils.isNotEmpty(vo.getPassword())) {
            po.setPassword(PasswordEncoder.encode(vo.getPassword()));
        }
        po.setSecretKey(UserSecretKeyGenerator.generate());
        po.setName(vo.getName());
        po.setMobile(vo.getMobile());
        po.setEmail(vo.getEmail());
        po.setPhoto(vo.getPhoto());
        po.setStatus(UserStatusVO.VALID);
        LocalDateTime now = LocalDateTime.now();
        po.setCreateTime(now);
        po.setCreateUser(operator);
        po.setUpdateTime(now);
        po.setUpdateUser(operator);
        po.setRemark(vo.getRemark());
        int affectedRows = sysUserMapper.insert(po);
        expectOneRow(affectedRows);
        Integer uid = Objects.requireNonNull(po.getUid());
        log.info("create user uid: {},user name:{}", uid, vo.getUserName());
        // 保存角色
        if (!CollectionUtils.isEmpty(vo.getRoleIds())) {
            addUserRoleService.saveRoles(vo.getRoleIds(), uid, operator);
        }
        return uid;
    }

}
