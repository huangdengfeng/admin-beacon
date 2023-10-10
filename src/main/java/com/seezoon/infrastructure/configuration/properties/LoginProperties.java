package com.seezoon.infrastructure.configuration.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录相关参数
 *
 * @author huangdengfeng
 * @date 2023/9/8 16:30
 */
@Getter
@Setter
public class LoginProperties {

    /**
     * 登录有效期,默认两小时
     */
    @NotNull
    private Duration accessTokenExpire = Duration.ofHours(2);
    /**
     * token 签名密钥
     */
    @NotEmpty
    @Size(min = 32)
    private String tokenSignKey;

}
