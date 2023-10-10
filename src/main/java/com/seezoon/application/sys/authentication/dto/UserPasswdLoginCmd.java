package com.seezoon.application.sys.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 账号密码登录
 *
 * @author huangdengfeng
 * @date 2022/10/12 12:49
 */
@Getter
@Setter
public class UserPasswdLoginCmd {

    @NotBlank
    @Schema(title = "用户名")
    private String username;
    @NotBlank
    @Length(min = 6)
    @Schema(title = "密码")
    private String password;

}
