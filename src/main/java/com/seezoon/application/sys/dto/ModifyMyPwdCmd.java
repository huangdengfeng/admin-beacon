package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/25 17:40
 */
@Getter
@Setter
public class ModifyMyPwdCmd {

    @Schema(title = "旧密码")
    @NotEmpty
    private String oldPassword;
    @Schema(title = "新密码")
    @NotEmpty
    private String newPassword;

}
