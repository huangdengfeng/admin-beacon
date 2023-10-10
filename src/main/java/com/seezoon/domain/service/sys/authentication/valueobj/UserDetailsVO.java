package com.seezoon.domain.service.sys.authentication.valueobj;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户信息
 *
 * @author huangdengfeng
 * @date 2023/9/10 16:57
 */
public class UserDetailsVO implements UserDetails {

    private final String username;
    private final Integer userId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String secretKey;

    public UserDetailsVO(String username, Integer userId,
            Collection<? extends GrantedAuthority> authorities, String secretKey) {
        this.username = Objects.requireNonNull(username);
        this.userId = Objects.requireNonNull(userId);
        this.authorities = Objects.requireNonNull(authorities);
        this.secretKey = Objects.requireNonNull(secretKey);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
