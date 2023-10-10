package com.seezoon.domain.service.sys.authentication;

import com.seezoon.domain.service.sys.authentication.support.JwtToken;
import com.seezoon.domain.service.sys.authentication.valueobj.TokenInfoVO;
import com.seezoon.infrastructure.configuration.properties.AppProperties;
import com.seezoon.infrastructure.configuration.properties.LoginProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Objects;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 登录token处理，如果想做到登录态自动续期，可以引入redis 存放tokenId
 *
 * @author huangdengfeng
 * @date 2023/9/8 16:27
 */
@Component
@Validated
public class LoginTokenService {

    private final JwtToken jwtToken;
    private final Duration accessTokenExpire;

    public LoginTokenService(AppProperties appProperties) {
        LoginProperties loginProperties = appProperties.getLogin();
        this.jwtToken = new JwtToken(loginProperties.getTokenSignKey());
        accessTokenExpire = loginProperties.getAccessTokenExpire();
    }

    /**
     * 为用户创建登录token
     *
     * @param userId
     * @param userSecretKey
     * @return not null
     */
    public String create(@NotNull Integer userId, @NotEmpty String userSecretKey) {
        String subject = String.valueOf(userId);
        TokenInfoVO tokenInfoVO = new TokenInfoVO(subject, null, genCheckSum(subject, userSecretKey));
        String accessToken = jwtToken.create(tokenInfoVO, accessTokenExpire);
        return accessToken;
    }


    /**
     * 获取用户Id
     *
     * @param accessToken
     * @return null if access token is invalid or expire
     */
    public TokenInfoVO getTokenInfo(@NotEmpty String accessToken) {
        TokenInfoVO tokenInfo = jwtToken.getTokenInfo(accessToken);
        return tokenInfo;
    }

    private String genCheckSum(String subject, String userSecretKey) {
        return DigestUtils.sha256Hex(subject + userSecretKey);
    }

    public boolean validateCheckSum(@NotEmpty String checkSum, @NotEmpty String subject,
            @NotEmpty String userSecretKey) {
        return Objects.equals(checkSum, genCheckSum(subject, userSecretKey));
    }

}
