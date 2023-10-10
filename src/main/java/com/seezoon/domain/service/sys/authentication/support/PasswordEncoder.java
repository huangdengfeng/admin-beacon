package com.seezoon.domain.service.sys.authentication.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * 密码处理
 *
 * @author hdf
 */
public class PasswordEncoder {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 加密密码
     *
     * @param password
     * @return null if password is empty
     */
    public static String encode(String password) {
        if (StringUtils.isEmpty(password)) {
            return null;
        } else {
            return encoder.encode(password);
        }
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        Assert.isTrue(null != rawPassword && rawPassword.length() > 0, "rawPassword must not empty");
        Assert.hasText(encodedPassword, "encodedPassword must not empty");
        return encoder.matches(rawPassword, encodedPassword);
    }

}
