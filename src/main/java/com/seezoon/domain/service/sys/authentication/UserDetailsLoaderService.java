package com.seezoon.domain.service.sys.authentication;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.valueobj.TokenInfoVO;
import com.seezoon.domain.service.sys.authentication.valueobj.UserDetailsVO;
import com.seezoon.domain.service.sys.authentication.valueobj.UserGrantedAuthority;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
import com.seezoon.infrastructure.configuration.security.UserDetailsLoader;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 登录认证处理
 * <p>
 * spring security filter会调用 {@link com.seezoon.infrastructure.configuration.security.WebSecurityConfig}
 * </p>
 *
 * @author huangdengfeng
 * @date 2023/9/10 10:43
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Validated
public class UserDetailsLoaderService implements UserDetailsLoader {

    // 一般换成redis
    private final Cache<Integer, UserDetailsVO> caches = CacheBuilder.newBuilder()
            // 设置容量大小 默认无限
            // .maximumSize(5000)
            // 设置超时时间
            .expireAfterWrite(2, TimeUnit.MINUTES).build();

    private final LoginTokenService loginTokenService;
    private final UserPermissionService userPermissionService;
    private final SysUserMapper sysUserMapper;

    @Override
    public UserDetails getUserDetails(@NotEmpty String accessToken) throws Throwable {
        TokenInfoVO tokenInfo = loginTokenService.getTokenInfo(accessToken);
        if (null == tokenInfo || StringUtils.isEmpty(tokenInfo.getSubject())) {
            return null;
        }
        Integer userId = Integer.valueOf(tokenInfo.getSubject());
        UserDetailsVO userDetails = null;
        try {
            userDetails = caches.get(userId, () -> this.getUserDetails0(userId));
        } catch (ExecutionException | UncheckedExecutionException e) {
            if (null != e.getCause()) {
                throw e.getCause();
            }
            throw e;
        }
        if (null == userDetails) {
            return null;
        }
        // check token info
        if (!loginTokenService.validateCheckSum(tokenInfo.getCheckSum(), tokenInfo.getSubject(),
                userDetails.getSecretKey())) {
            log.error("token checksum not match,userId:{}", userId);
            return null;
        }
        return userDetails;
    }

    public UserDetailsVO getUserDetails0(@NotNull Integer userId) {
        SysUserPO sysUserPO = sysUserMapper.selectByPrimaryKey(userId);
        if (null == sysUserPO) {
            log.warn("userId [%s] not exists", userId);
            throw ExceptionFactory.bizException(ErrorCode.USER_NOT_EXISTS);
        }
        Byte userStatus = sysUserPO.getStatus();
        if (UserStatusVO.inValid(userStatus)) {
            throw ExceptionFactory.bizException(ErrorCode.USER_STATUS_INVALID);
        }
        if (UserStatusVO.isLocked(userStatus)) {
            throw ExceptionFactory.bizException(ErrorCode.USER_STATUS_LOCKED);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roleCodes = userPermissionService.getUserRoles(userId);
        for (String roleCode : roleCodes) {
            authorities.add(new UserGrantedAuthority(roleCode, true));
        }
        List<String> permissions = userPermissionService.getPermissions(userId);
        for (String permission : permissions) {
            authorities.add(new UserGrantedAuthority(permission, false));
        }
        UserDetailsVO userDetails = new UserDetailsVO(sysUserPO.getUserName(), userId, authorities,
                sysUserPO.getSecretKey());
        return userDetails;
    }


}
