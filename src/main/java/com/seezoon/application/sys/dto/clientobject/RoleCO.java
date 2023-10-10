package com.seezoon.application.sys.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/26 14:13
 */
@Getter
@Setter
public class RoleCO {

    @Schema(title = "主键")
    private Integer id;
    @Schema(title = "角色编码")
    private String code;
    @Schema(title = "角色名称")
    private String name;
    @Schema(title = "状态", description = "字典record-status")
    private Byte status;

    public RoleCO(Integer id, String code, String name, Byte status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
    }
}
