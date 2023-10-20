package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.clientobject.DownFileCO;
import com.seezoon.domain.service.sys.FileService;
import jakarta.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 下载
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class DownFileQryExe {

    private final FileService fileService;

    public DownFileCO execute(@NotEmpty String relativePath) throws IOException {
        InputStream inputStream = fileService.download(relativePath);
        String contentType = Files.probeContentType(Paths.get(fileService.getFileName(relativePath)));
        return new DownFileCO(fileService.getFileName(relativePath), inputStream, contentType);
    }

}
