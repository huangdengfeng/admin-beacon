package com.seezoon.infrastructure.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 认证、授权配置
 *
 * @author huangdengfeng
 * @date 2022/10/11 09:39
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

    private static final String PUBLIC_ANT_PATH = "/public/**";
    private static final String LOGIN_ANT_PATH = "/sys/login/**";
    private static final String[] STATIC_ANT_PATH =
            {"/pages/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "*.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            UserDetailsLoader userDetailsLoader) throws Exception {
        // @formatter:off
        http.csrf(csrf->csrf.disable())
                .formLogin(formLogin->formLogin.disable())
                .logout(logout->logout.disable())
                .sessionManagement(sessionManagement->sessionManagement.disable())
                .exceptionHandling(exceptionHandling->exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        // 这里实际无效，因为和ContorllerAdvice 统一拦截异常有冲突，这里返回403 是在WebExceptionAdvice 中处理
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()));

        http.authorizeHttpRequests(authorize->authorize
                .requestMatchers(PUBLIC_ANT_PATH,LOGIN_ANT_PATH).permitAll()
                .anyRequest().authenticated());

        http.addFilterBefore(new TokenAuthenticationFilter(userDetailsLoader),UsernamePasswordAuthenticationFilter.class);
        // @formatter:on
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(STATIC_ANT_PATH);
    }

}
