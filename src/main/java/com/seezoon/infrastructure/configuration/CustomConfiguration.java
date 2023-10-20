package com.seezoon.infrastructure.configuration;

import com.seezoon.infrastructure.configuration.properties.AppProperties;
import com.seezoon.infrastructure.file.FileHandler;
import com.seezoon.infrastructure.file.LocalFileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义装配
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class CustomConfiguration {

    @Bean
    public FileHandler fileHandler(AppProperties appProperties) {
        return new LocalFileHandler(appProperties.getUpload());
    }
}
