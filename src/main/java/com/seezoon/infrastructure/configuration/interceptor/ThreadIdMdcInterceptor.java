package com.seezoon.infrastructure.configuration.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 打印线程号
 * <p>
 * log pattern use %X{tid:-默认值} or %X{tid}
 * </p>
 *
 * @author huangdengfeng
 * @date 2023/8/27 22:35
 */
public class ThreadIdMdcInterceptor implements HandlerInterceptor {

    private static final String NAME = "tid";
    private static final int LENGTH = 10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(NAME, RandomStringUtils.randomAlphanumeric(LENGTH));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        MDC.remove(NAME);
    }
}
