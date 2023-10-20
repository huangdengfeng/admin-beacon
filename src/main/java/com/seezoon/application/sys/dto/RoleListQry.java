package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/26 09:40
 */
@Getter
@Setter
public class RoleListQry {

    @Schema(title = "只查询有效的")
    private boolean onlyValid = false;
}
