package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.UploadCmd;
import com.seezoon.application.sys.dto.clientobject.FileCO;
import com.seezoon.domain.service.sys.FileService;
import com.seezoon.infrastructure.configuration.properties.AppProperties;
import com.seezoon.infrastructure.configuration.properties.UploadProperties;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 上传
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class UploadCmdExe {

    private final FileService fileService;
    private final AppProperties appProperties;

    public Response<FileCO> execute(@NotNull @Valid UploadCmd cmd) {
        UploadProperties upload = appProperties.getUpload();
        String relativePath = fileService.upload(cmd.getOriginalFilename(), cmd.getContentType(), cmd.getIn());
        return Response
                .success(new FileCO(upload.getUrlPrefix() + relativePath, relativePath, cmd.getOriginalFilename()));
    }

    public Response<List<FileCO>> executeBatch(@NotEmpty @Valid List<UploadCmd> cmds) {
        UploadProperties upload = appProperties.getUpload();
        List<FileCO> data = new ArrayList<>();
        for (UploadCmd cmd : cmds) {
            String relativePath = fileService.upload(cmd.getOriginalFilename(), cmd.getContentType(), cmd.getIn());
            data.add(new FileCO(upload.getUrlPrefix() + relativePath, relativePath, cmd.getOriginalFilename()));
        }
        return Response.success(data);
    }

}
