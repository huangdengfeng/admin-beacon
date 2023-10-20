package com.seezoon.domain.dao.po;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserPO {

    private Integer uid;

    private String userName;

    private String password;

    private String secretKey;

    private String name;

    private String mobile;

    private String email;

    private String photo;

    private Byte status;

    private LocalDateTime createTime;

    private Integer createUser;

    private LocalDateTime updateTime;

    private Integer updateUser;

    private String remark;

    @Getter
    @Setter
    public static class Condition {

        private Integer uid;
        private String userName;
        private String fuzzyName;
        private Byte status;
        private Boolean includeSysAdmin;
    }
}