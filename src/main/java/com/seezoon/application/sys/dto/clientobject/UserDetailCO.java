package com.seezoon.application.sys.dto.clientobject;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/24 17:23
 */
@Getter
@Setter
public class UserDetailCO extends UserCO {

    private List<Integer> roleIds;
}
