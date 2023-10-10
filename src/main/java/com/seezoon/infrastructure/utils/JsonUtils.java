package com.seezoon.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seezoon.infrastructure.configuration.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * json 处理 spring 环境下使用
 *
 * @author huangdengfeng
 * @date 2023/1/5 10:37
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = SpringContextHolder.getBean(ObjectMapper.class);


    /**
     * java 对象转json
     *
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("to json error", e);
            throw new RuntimeException(e);
        }
    }

}
