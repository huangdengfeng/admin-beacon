package com.seezoon.application.sys.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParamCO {

    @Schema(title = "上传文件服务器地址")
    private String fileUploadUrl;
    @Schema(title = "文件服务器地址")
    private String fileServerUrl;
    @Schema(title = "字典")
    private Map<String, List<DictCO>> dicts = Collections.emptyMap();

}
