package com.seezoon.application.sys.dto.clientobject;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2022/10/13 00:47
 */
@Getter
@Setter
@AllArgsConstructor
public class DownFileCO {

    /**
     * 原始文件名
     */
    private String name;
    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 文件类型
     */
    private String contentType;
}
