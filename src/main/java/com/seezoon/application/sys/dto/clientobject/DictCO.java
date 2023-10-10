package com.seezoon.application.sys.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/19 09:23
 */
@Getter
@Setter
public class DictCO {

    @Schema(title = "字典值", description = "通用用于提交")
    private Object value;
    @Schema(title = "字典标签", description = "通常是显式使用")
    private String name;
    @Schema(title = "是否禁用", description = "禁用一般置为不可选")
    private boolean disabled;

    public DictCO(Object value, String name, boolean disabled) {
        this.value = value;
        this.name = name;
        this.disabled = disabled;
    }
}
