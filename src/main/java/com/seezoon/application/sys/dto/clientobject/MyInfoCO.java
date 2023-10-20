package com.seezoon.application.sys.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyInfoCO {

    @Schema(title = "用户名")
    private String userName;
    @Schema(title = "姓名")
    private String name;
    @Schema(title = "手机号")
    private String mobile;
    @Schema(title = "邮件")
    private String email;
    @Schema(title = "照片")
    private String photo;
    @Schema(title = "角色编码")
    private List<String> roles;
    @Schema(title = "角色名称")
    private List<String> roleNames;
    @Schema(title = "权限编码")
    private List<String> permissions;
}
