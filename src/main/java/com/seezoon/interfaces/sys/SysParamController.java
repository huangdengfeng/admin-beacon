package com.seezoon.interfaces.sys;

import com.seezoon.application.sys.dto.clientobject.ParamCO;
import com.seezoon.application.sys.executor.ParamQryExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "系统参数", description = "系统参数及字典")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/param")
public class SysParamController {

    private final ParamQryExe paramQryExe;

    @Operation(summary = "查询系统参数", description = "")
    @GetMapping("/qry")
    public Response<ParamCO> qryParam() {
        return paramQryExe.execute();
    }

}
