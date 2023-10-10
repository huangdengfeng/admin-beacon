package com.seezoon.domain.dao.po;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SysUserRolePO {

    private Integer uid;
    private Integer roleId;
    private Integer createUser;
    private LocalDateTime createTime;

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class SysUserRoleKey {

        private Integer uid;
        private Integer roleId;
    }
}