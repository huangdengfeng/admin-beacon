package com.seezoon.infrastructure.configuration.security;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户信息接口
 *
 * @author huangdengfeng
 * @date 2023/9/10 22:54
 */
public interface UserDetailsLoader {

    /**
     * 通过token获取用户信息
     *
     * @param accessToken
     * @return
     */
    UserDetails getUserDetails(@NotEmpty String accessToken) throws Throwable;
}
