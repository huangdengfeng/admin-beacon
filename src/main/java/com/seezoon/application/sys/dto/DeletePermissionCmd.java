package com.seezoon.application.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/9/25 19:42
 */
@Getter
@Setter
public class DeletePermissionCmd {

    @Schema(title = "主键")
    @NotNull
    private Integer id;
}
