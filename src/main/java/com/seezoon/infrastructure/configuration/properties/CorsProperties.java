package com.seezoon.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * 前后端分离，跨域很常见，框架默认开启，线上为了安全可以设置allowedOrigins
 *
 * also see {@link CorsConfiguration}
 */
@Getter
@Setter
public class CorsProperties {

    private boolean enable = false;
    /**
     * 路径{@link org.springframework.util.AntPathMatcher}
     */
    private String mapping = "/**";
    // 配置时候可以逗号分隔,可以写实际域名,如https://www.seeezoon.com
    private String[] allowedOriginPatterns = {CorsConfiguration.ALL};
    private String[] allowedMethods = {CorsConfiguration.ALL};
    private String[] allowedHeaders = {CorsConfiguration.ALL};
    private boolean allowCredentials = true;
    private long maxAge = 1800;
}