package com.seezoon.domain.service.sys.authentication;

import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.support.PasswordEncoder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 用户密码验证服务
 *
 * @author huangdengfeng
 * @date 2023/9/10 23:30
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
public class UserPasswdVerifyService {

    private final SysUserMapper sysUserMapper;

    public boolean verify(@NotEmpty String username, @NotEmpty String password) {
        SysUserPO po = sysUserMapper.selectByUserName(username);
        String userPassword = po.getPassword();
        if (po == null || StringUtils.isEmpty(userPassword)) {
            return false;
        }
        if (PasswordEncoder.matches(password, userPassword)) {
            return true;
        }
        return false;
    }

    public boolean verify(@NotNull Integer uid, @NotEmpty String password) {
        SysUserPO po = sysUserMapper.selectByPrimaryKey(uid);
        String userPassword = po.getPassword();
        if (po == null || StringUtils.isEmpty(userPassword)) {
            return false;
        }
        if (PasswordEncoder.matches(password, userPassword)) {
            return true;
        }
        return false;
    }

}
