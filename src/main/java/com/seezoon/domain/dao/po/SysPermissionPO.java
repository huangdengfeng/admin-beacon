package com.seezoon.domain.dao.po;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysPermissionPO {

    private Integer id;

    private String code;

    private String name;

    private Integer parentId;
    
    private String parentIds;

    private Byte status;

    private Integer createUser;

    private LocalDateTime createTime;

    private Integer updateUser;

    private LocalDateTime updateTime;


}