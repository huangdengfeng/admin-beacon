package com.seezoon;

import com.seezoon.infrastructure.configuration.properties.AppProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(basePackages = "com.seezoon.domain.dao.mapper")
@EnableTransactionManagement
@EnableConfigurationProperties({AppProperties.class})
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
