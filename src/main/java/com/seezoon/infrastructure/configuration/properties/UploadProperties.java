package com.seezoon.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件上传配置
 */
@Getter
@Setter
public class UploadProperties {

    /**
     * 上传域名，一般和当前web部署域名一致，如https://xxx.com
     */
    private String uploadUrl;
    /**
     * 可访问的网址前缀，如https://xxx.com
     */
    private String urlPrefix;
    // 图片压缩开关
    private boolean enableImageCompress = false;
    // 图片输出质量 1 百分百输出 < 1
    private float imageQuality = 1;
    // 缩放1 为不缩放 < 1
    private double imageScale = 1;
    // 上传目录
    private String directory = "./upload";

}