package com.seezoon.application.sys.dto.clientobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.infrastructure.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCO {

    @Schema(title = "用户ID")
    private Integer uid;
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
    @Schema(title = "状态")
    private Byte status;
    @Schema(title = "状态名称")
    private String statusName;
    @Schema(title = "创建时间")
    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    private LocalDateTime createTime;
    @Schema(title = "修改时间")
    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    private LocalDateTime updateTime;
    @Schema(title = "备注")
    private String remark;
}
