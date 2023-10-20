package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/25 17:40
 */
@Getter
@Setter
public class ModifyUserPwdCmd {

    @Schema(title = "用户ID")
    @NotNull
    private Integer uid;
    @Schema(title = "密码")
    @NotEmpty
    private String password;

}
