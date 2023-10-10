package com.seezoon.infrastructure.configuration.security;

import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.BaseException;
import com.seezoon.infrastructure.utils.JsonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 基于token的登录态处理
 *
 * @author huangdengfeng
 * @date 2023/9/10 10:01
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";
    private static int PREFIX_LENGTH = 0;
    private final UserDetailsLoader userDetailsLoader;

    public TokenAuthenticationFilter(UserDetailsLoader userDetailsLoader) {
        PREFIX_LENGTH = PREFIX.length();
        this.userDetailsLoader = Objects.requireNonNull(userDetailsLoader);
    }

    @Override
    protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(PREFIX)
                || authorization.length() <= PREFIX_LENGTH) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(PREFIX_LENGTH);
        // 如果有异常， spring security 后续会当为登录处理
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsLoader.getUserDetails(token);
        } catch (BaseException e) {
            log.error("get user details biz error", e);
            // 如果是自定义异常则直接抛出
            this.output(response, e.getcode(), e.getMessage());
            return;
        } catch (Throwable e) {
            log.error("get user details unkown error", e);
            // 这里面的异常会被spring security 后面的fitlter 转换成401 or 403,所以有异常提前抛出,避免抖动造成用户退出
            this.output(response, ErrorCode.USER_AUTHORIZATION.code(), ErrorCode.USER_AUTHORIZATION.msg());
            return;
        }
        if (null != userDetails) {
            Authentication authentication = new UserAuthenticationToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    private void output(HttpServletResponse response, int code, String msg) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String json = JsonUtils.toJson(Response.error(code, msg));
        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }


    public static class UserAuthenticationToken extends AbstractAuthenticationToken {

        private final Object principal;

        public UserAuthenticationToken(UserDetails userDetails) {
            super(userDetails.getAuthorities());
            setAuthenticated(true);
            setDetails(userDetails);
            principal = userDetails;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }
    }
}
