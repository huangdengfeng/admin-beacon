package com.seezoon.domain.service.sys.valueobj;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户信息
 *
 * @author huangdengfeng
 * @date 2023/8/27 10:00
 */
@Getter
@Setter
public class ModifyUserVO {

    @NotNull
    private Integer uid;
    @NotBlank
    private String userName;
    @NotBlank
    private String name;
    private String mobile;
    private String email;
    private String photo;
    @NotNull
    private Byte status;
    private String remark;
    private Set<Integer> roleIds;

    public ModifyUserVO(Integer uid, String userName, String name, Byte status) {
        this.uid = uid;
        this.userName = userName;
        this.name = name;
        this.status = status;
    }
}
