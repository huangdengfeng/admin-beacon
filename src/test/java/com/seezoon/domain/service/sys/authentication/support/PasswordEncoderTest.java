package com.seezoon.domain.service.sys.authentication.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 密码测试
 *
 * @author huangdengfeng
 * @date 2023/9/11 14:24
 */
class PasswordEncoderTest {

    @Test
    void testMatch() {
        String password = "123456";
        String encoded = PasswordEncoder.encode(password);
        Assertions.assertTrue(PasswordEncoder.matches(password, encoded));
        Assertions.assertTrue(!PasswordEncoder.matches("1234567", encoded));
    }

    @Test
    void testMatchStatic() {
        String password = "123456";
        String encodedPassword = "$2a$10$dW0IseBeJA51vW0v1xrFf.ldxJMKoqSCb8lHS5G2YUTbSPQ65.vIq";
        Assertions.assertTrue(PasswordEncoder.matches(password, encodedPassword));
        Assertions.assertTrue(!PasswordEncoder.matches("1234567", encodedPassword));

    }

}