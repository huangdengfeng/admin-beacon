package com.seezoon.application.sys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户明细查询
 *
 * @author dfenghuang
 * @date 2023/9/24 17:18
 */
@Getter
@Setter
public class UserDetailQry {

    @NotNull
    private Integer uid;

    public UserDetailQry() {
    }

    public UserDetailQry(Integer uid) {
        this.uid = uid;
    }
}
