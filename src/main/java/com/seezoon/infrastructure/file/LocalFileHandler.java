package com.seezoon.infrastructure.file;

import com.seezoon.infrastructure.configuration.properties.UploadProperties;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 本地文件存储处理
 *
 * @author hdf
 */
public class LocalFileHandler implements FileHandler {

    private UploadProperties uploadProperties;

    public LocalFileHandler(UploadProperties uploadProperties) {
        Assert.hasText(uploadProperties.getDirectory(), "UploadProperties directory must not be empty");
        this.uploadProperties = uploadProperties;
    }

    /**
     * 上传文件
     * <p>
     * 目的地目录不存在会自动创建,文件存在则会覆盖
     *
     * @param relativePath
     * @param contentType
     * @param in           会被关闭{@link FileUtils#copyInputStreamToFile(InputStream, File)}
     * @throws IOException
     */
    @Override
    public void upload(String relativePath, String contentType, InputStream in) throws IOException {
        Assert.hasLength(relativePath, "relativePath must not be empty");
        Assert.notNull(in, "inputStream must not be null");
        Path storePath = Path.of(uploadProperties.getDirectory(), relativePath);

        if (isImage(contentType) && uploadProperties.isEnableImageCompress()) {
            InputStream compressedInputStream =
                    imageCompress(in, uploadProperties.getImageQuality(), uploadProperties.getImageScale());
            FileUtils.copyInputStreamToFile(compressedInputStream, storePath.toFile());
        } else {
            FileUtils.copyInputStreamToFile(in, storePath.toFile());
        }
    }

    @Override
    public InputStream download(String relativePath) throws IOException {
        Assert.hasLength(relativePath, "relativePath must not be empty");
        Path storePath = Path.of(uploadProperties.getDirectory(), relativePath);
        if (!Files.exists(storePath)) {
            throw ExceptionFactory.bizException(ErrorCode.FILE_NOT_EXISTS);
        }
        return Files.newInputStream(storePath);
    }

    @Override
    public void delete(String relativePath) throws IOException {
        Assert.hasLength(relativePath, "relativePath must not be empty");
        Path storePath = Path.of(uploadProperties.getDirectory(), relativePath);
        // 文件不存在返回 false
        if (Files.isDirectory(storePath)) {
            throw new RuntimeException(storePath + " is directory,can not delete ");
        }
        Files.deleteIfExists(storePath);
    }

    @Override
    public String getUrl(String relativePath) {
        return StringUtils.isNotBlank(relativePath) ? uploadProperties.getUrlPrefix() + relativePath : null;
    }

    @Override
    public String getUrlPrefix() {
        return uploadProperties.getUrlPrefix();
    }

    @Override
    public void close() throws Exception {

    }
}
