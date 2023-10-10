package com.seezoon.infrastructure.configuration.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 打印响应参数
 *
 * @author huangdengfeng
 * @date 2023/8/27 19:22
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ResponseBodyAdvice implements
        org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        this.logResponse(body);
        return body;
    }

    private void logResponse(Object result) {
        if (!log.isDebugEnabled()) {
            return;
        }
        if (null == result) {
            return;
        }
        try {
            log.debug("response:" + objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            // swallow it
            log.error("log response error", e);
        }
    }
}
