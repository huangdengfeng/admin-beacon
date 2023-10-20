package com.seezoon.domain.service.sys;

import static com.seezoon.infrastructure.utils.AffectedRowChecker.expectOneRow;

import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.UserPasswdVerifyService;
import com.seezoon.domain.service.sys.authentication.support.PasswordEncoder;
import com.seezoon.domain.service.sys.support.UserSecretKeyGenerator;
import com.seezoon.domain.service.sys.valueobj.ModifyUserVO;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

/**
 * 修改用户信息
 *
 * @author huangdengfeng
 * @date 2023/8/26 18:27
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
@Validated
public class ModifyUserService {

    private final AddUserRoleService addUserRoleService;
    private final UserPasswdVerifyService userPasswdVerifyService;

    private final SysUserMapper sysUserMapper;

    public void modify(@Valid @NotNull ModifyUserVO vo, @NotNull Integer operator) {
        UserStatusVO.check(vo.getStatus());
        checkUserName(vo.getUserName(), vo.getUid());

        SysUserPO po = sysUserMapper.selectByPrimaryKey(vo.getUid());
        if (null == po) {
            throw ExceptionFactory.bizException(ErrorCode.USER_NOT_EXISTS);
        }
        po.setUid(vo.getUid());
        po.setUserName(vo.getUserName());
        po.setName(vo.getName());
        po.setMobile(vo.getMobile());
        po.setEmail(vo.getEmail());
        po.setPhoto(vo.getPhoto());
        po.setStatus(vo.getStatus());
        po.setUpdateTime(LocalDateTime.now());
        po.setUpdateUser(operator);
        po.setRemark(vo.getRemark());
        int affectedRows = sysUserMapper.updateByPrimaryKey(po);
        expectOneRow(affectedRows);
        if (!CollectionUtils.isEmpty(vo.getRoleIds())) {
            addUserRoleService.saveRoles(vo.getRoleIds(), vo.getUid(), operator);
        }
    }

    public void modifyPwd(@NotNull Integer uid, @NotEmpty String password,
            @NotNull Integer operator) {
        SysUserPO toUpdate = new SysUserPO();
        toUpdate.setUid(uid);
        toUpdate.setPassword(PasswordEncoder.encode(password));
        toUpdate.setUpdateUser(operator);
        toUpdate.setSecretKey(UserSecretKeyGenerator.generate());
        toUpdate.setUpdateTime(LocalDateTime.now());
        int affectedRow = sysUserMapper.updateByPrimaryKeySelective(toUpdate);
        expectOneRow(affectedRow);
    }


    public void modifyPwd(@NotNull Integer uid, @NotEmpty String oldPassword, @NotEmpty String newPassword,
            @NotNull Integer operator) {
        // 验密
        boolean verified = userPasswdVerifyService.verify(uid, oldPassword);
        if (!verified) {
            throw ExceptionFactory.bizException(ErrorCode.ORIGINAL_PASSWD_WRONG);
        }
        this.modifyPwd(uid, newPassword, operator);
    }


    private void checkUserName(String userName, Integer uid) {
        SysUserPO po = sysUserMapper.selectByUserName(userName);
        if (null != po && !Objects.equals(po.getUid(), uid)) {
            throw ExceptionFactory.bizException(ErrorCode.SYS_USER_NAME_EXISTS);
        }
    }
}
