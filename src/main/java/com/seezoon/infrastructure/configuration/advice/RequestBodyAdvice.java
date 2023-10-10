package com.seezoon.infrastructure.configuration.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

/**
 * 打印请求参数
 *
 * @author huangdengfeng
 * @date 2023/8/27 19:11
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class RequestBodyAdvice extends RequestBodyAdviceAdapter {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        this.logRequest(request.getRequestURI().toString(), body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    private void logRequest(String uri, Object body) {
        if (!log.isDebugEnabled()) {
            return;
        }
        if (null == body) {
            return;
        }
        try {
            log.debug("start processing:" + uri);
            log.debug("request:" + objectMapper.writeValueAsString(body));
        } catch (Exception e) {
            // swallow it
            log.error("log reqeust error", e);
        }
    }
}
