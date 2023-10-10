package com.seezoon.domain.service.sys.support;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 生成用户安全密钥
 *
 * @author huangdengfeng
 * @date 2023/9/13 14:28
 */
public class UserSecretKeyGenerator {

    private static final int LENGTH = 32;

    public static String generate() {
        return RandomStringUtils.randomAlphanumeric(LENGTH);
    }
}
