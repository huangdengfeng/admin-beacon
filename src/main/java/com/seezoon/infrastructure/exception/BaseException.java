package com.seezoon.infrastructure.exception;

/**
 * Base Exception is the parent of all exceptions
 *
 * @author huangdengfeng
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e);
    }

    public BaseException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public int getcode() {
        return code;
    }

    public void setcode(int code) {
        this.code = code;
    }
}
