package com.seezoon.interfaces.sys;

import com.seezoon.application.sys.dto.UploadCmd;
import com.seezoon.application.sys.dto.clientobject.DownFileCO;
import com.seezoon.application.sys.dto.clientobject.FileCO;
import com.seezoon.application.sys.executor.DownFileQryExe;
import com.seezoon.application.sys.executor.UploadCmdExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件
 *
 * @author dfenghuang
 * @date 2022/10/12 20:50
 */
@RestController
@RequestMapping("/sys/file")
@RequiredArgsConstructor
@Validated
public class SysFileController {

    private final UploadCmdExe uploadCmdExe;
    private final DownFileQryExe downFileQryExe;

    @Operation(summary = "单文件上传")
    @PostMapping("/upload")
    public Response<FileCO> upload(@NotNull @RequestParam MultipartFile file) throws IOException {
        UploadCmd uploadCmd =
                new UploadCmd(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getInputStream());
        return uploadCmdExe.execute(uploadCmd);
    }

    @Operation(summary = "批量上传")
    @PostMapping("/upload_batch")
    public Response<List<FileCO>> upload(@NotNull @RequestParam MultipartFile[] files) throws IOException {
        List<UploadCmd> cmds = new ArrayList<>();
        for (MultipartFile file : files) {
            cmds.add(new UploadCmd(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream()));
        }
        return uploadCmdExe.executeBatch(cmds);
    }

    /**
     * 访问文件
     *
     * @param relativePath 相对路径
     * @param down 是否下载
     * @param response
     * @throws IOException
     */
    @Operation(summary = "文件访问")
    @GetMapping("/access")
    public void download(@NotEmpty String relativePath, boolean down,
            HttpServletResponse response) throws IOException {
        DownFileCO fileCO = downFileQryExe.execute(relativePath);
        response.setContentType(fileCO.getContentType());
        try (InputStream inputStream = fileCO.getInputStream();
                OutputStream outputStream = response.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                BufferedInputStream bin = new BufferedInputStream(inputStream)) {
            if (down) {
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileCO.getName(), StandardCharsets.UTF_8));
            }
            // 1M 一写
            byte[] buffer = new byte[1024 * 1024];
            int len = 0;
            while (-1 != (len = bin.read(buffer))) {
                bos.write(buffer, 0, len);
            }
        }
    }
}