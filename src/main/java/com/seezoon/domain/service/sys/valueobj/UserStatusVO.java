package com.seezoon.domain.service.sys.valueobj;

import com.seezoon.infrastructure.exception.Assertion;

/**
 * 用户状态
 */
public class UserStatusVO {

    public static final String DICT_TYPE = "sys-user-status";
    /**
     * 有效
     */
    public static final byte VALID = 1;
    /**
     * 无效
     */
    public static final byte INVALID = 2;

    /**
     * 锁定
     */
    public static final byte LOCKED = 3;

    public static void check(Byte status) {
        Assertion.notNull(status);
        Assertion.isTrue(status == VALID
                || status == INVALID
                || status == LOCKED, "user status incorrect");
    }

    public static boolean valid(byte status) {
        return status == VALID;
    }

    public static boolean inValid(byte status) {
        return status == INVALID;
    }

    public static boolean isLocked(byte status) {
        return status == LOCKED;
    }
}
