package com.seezoon.application.sys.dto.clientobject;

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
public class FileCO {

    /**
     * 文件全路径
     */
    private String url;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 原始文件名
     */
    private String name;
}
