package com.seezoon.domain.service.sys.valueobj;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/23 08:54
 */
@Getter
@Setter
public class ModifyPermissionVO {

    @NotNull
    private Integer id;
    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    private Integer parentId;
    @NotNull
    private Byte status;

    public ModifyPermissionVO(Integer id, String code, String name, Integer parentId, Byte status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.status = status;
    }
}
