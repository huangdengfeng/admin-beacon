package com.seezoon.infrastructure.exception;

/**
 * Extends your error codes in your App by implements this Interface
 *
 * @author huangdengfeng
 */
public interface ErrorDefinition {

    /**
     * Do a good job of error code planning
     *
     * @return
     */
    int code();

    String msg();

    int type();
}