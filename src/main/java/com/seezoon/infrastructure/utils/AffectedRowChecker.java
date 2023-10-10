package com.seezoon.infrastructure.utils;

import com.seezoon.infrastructure.exception.Assertion;

/**
 * @author huangdengfeng
 * @date 2023/9/8 16:17
 */
public class AffectedRowChecker {

    public static void expectRow(int expect, int actual) {
        Assertion.isTrue(expect == actual, "expect: " + expect + " ,actual:" + actual);
    }

    public static void expectOneRow(int actual) {
        expectRow(1, actual);
    }

    public static void expectLeOneRow(int actual) {
        Assertion.isTrue(actual <= 1, "expect: <= 1 ,actual:" + actual);
    }
}
