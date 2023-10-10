package com.seezoon.domain.service.sys.valueobj;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/23 08:54
 */
@Getter
@Setter
public class AddPermissionVO {

    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    /**
     * 父节点，没有请传null
     */
    private Integer parentId;

    public AddPermissionVO(String code, String name, Integer parentId) {
        this.code = code;
        this.name = name;
        this.parentId = parentId;
    }
}
