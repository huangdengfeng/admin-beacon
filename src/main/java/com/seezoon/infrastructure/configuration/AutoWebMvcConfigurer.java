package com.seezoon.infrastructure.configuration;

import com.seezoon.infrastructure.configuration.interceptor.ThreadIdMdcInterceptor;
import com.seezoon.infrastructure.configuration.properties.AppProperties;
import com.seezoon.infrastructure.configuration.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc 配置
 *
 * @author huangdengfeng
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AutoWebMvcConfigurer implements WebMvcConfigurer {

    private static final String ALL = "/**";
    private final AppProperties appProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThreadIdMdcInterceptor()).addPathPatterns(ALL);
    }

    /**
     * 跨域很常见，默认框架参数开启，如果想更安全，allowedOriginPatterns，如https://www.seezoon.com
     *
     * 如果使用spring security 必须配置{@code http.cors()} 否则以下配置无效
     *
     * 实际spring boot 的处理类{@link org.springframework.web.cors.reactive.CorsWebFilter}
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsProperties cors = appProperties.getCors();
        if (!cors.isEnable()) {
            return;
        }
        registry.addMapping(cors.getMapping()).allowedOriginPatterns(cors.getAllowedOriginPatterns())
                .allowedHeaders(cors.getAllowedHeaders()).allowedMethods(cors.getAllowedMethods())
                .allowCredentials(cors.isAllowCredentials()).maxAge(cors.getMaxAge());
    }

}
