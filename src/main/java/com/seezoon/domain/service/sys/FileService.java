package com.seezoon.domain.service.sys;

import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import com.seezoon.infrastructure.file.FileHandler;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author dfenghuang
 * @date 2022/10/13 01:02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private static final String LINE_THROUGH = "-";
    private static final String SLASH = "/";
    private final FileHandler fileHandler;

    public String upload(@NotEmpty String originalFilename, @NotEmpty String contentType, @NotNull InputStream in) {
        String fileId = createFileId();
        String newName = rename(fileId, originalFilename);
        String relativePath = createRelativeDirectory() + newName;
        try {
            fileHandler.upload(relativePath, contentType, in);
        } catch (IOException e) {
            log.error("upload error", e);
            throw ExceptionFactory.bizException(ErrorCode.FILE_UPLOAD_FAILED.code(),
                    String.format(ErrorCode.FILE_UPLOAD_FAILED.msg(), e.getMessage()));
        }
        return relativePath;
    }

    public InputStream download(@NotEmpty String relativePath) throws IOException {
        return this.fileHandler.download(relativePath);
    }


    private String createFileId() {
        return UUID.randomUUID().toString().replaceAll(LINE_THROUGH, StringUtils.EMPTY);
    }

    /**
     * 生成新的文件名
     *
     * @param fileId
     * @param originalFileName
     * @return
     */
    public String rename(String fileId, String originalFileName) {
        return fileId + getFileSuffix(originalFileName);
    }

    public String getFileName(@NotEmpty String relativePath) {
        return relativePath.substring(relativePath.lastIndexOf(SLASH) + 1);
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 包含后缀
     * @return
     */
    public String getFileSuffix(String fileName) {
        Assert.hasText(fileName, "fileName must not be empty");
        String suffix = "";
        int lastIndex;
        if (-1 != (lastIndex = fileName.lastIndexOf("."))) {
            suffix = fileName.substring(lastIndex);
        }
        return suffix;
    }

    /**
     * 生成相对目录
     *
     * @return
     */
    private String createRelativeDirectory() {
        LocalDate now = LocalDate.now();
        return SLASH + now.getYear() + SLASH + now.getMonthValue() + SLASH + now.getDayOfMonth() + SLASH;
    }
}
