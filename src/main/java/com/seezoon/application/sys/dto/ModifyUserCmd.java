package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户信息
 *
 * @author huangdengfeng
 * @date 2023/8/27 10:00
 */
@Getter
@Setter
public class ModifyUserCmd {

    @NotNull
    @Schema(title = "用户id")
    private Integer uid;
    @NotBlank
    @Size(max = 50)
    @Schema(title = "用户名", description = "用户名是系统唯一的")
    private String userName;
    @NotBlank
    @Size(max = 50)
    @Schema(title = "姓名")
    private String name;

    @Size(max = 20)
    @Schema(title = "手机号")
    private String mobile;

    @Size(max = 20)
    @Schema(title = "邮件")
    private String email;

    @Size(max = 100)
    @Schema(title = "头像", description = "相对路径")
    private String photo;

    @NotNull
    @Schema(title = "状态", description = "字典sys-user-status")
    private Byte status;
    @Size(max = 200)
    @Schema(title = "备注")
    private String remark;

    @Schema(title = "角色")
    private Set<Integer> roleIds;
}
