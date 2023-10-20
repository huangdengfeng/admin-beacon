package com.seezoon.application.sys.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2022/10/13 00:40
 */
@Getter
@Setter
@AllArgsConstructor
public class UploadCmd {

    @NotEmpty
    private String originalFilename;
    @NotEmpty
    private String contentType;
    private long size;
    @JsonIgnore
    private InputStream in;

}
