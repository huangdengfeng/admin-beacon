package com.seezoon.domain.service.sys.valueobj;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加用户值对象
 *
 * @author huangdengfeng
 * @date 2023/8/26 18:47
 */
@Getter
@Setter
public class AddUserVO {

    @NotBlank
    private String userName;
    private String password;
    @NotBlank
    private String name;
    private String mobile;
    private String email;
    private String photo;
    private String remark;

    private Set<Integer> roleIds;

    public AddUserVO(String userName, String name) {
        this.userName = userName;
        this.name = name;
    }
}
