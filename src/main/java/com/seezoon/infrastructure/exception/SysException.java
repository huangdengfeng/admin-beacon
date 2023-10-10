package com.seezoon.infrastructure.exception;

/**
 * System Exception is unexpected Exception, retry might work again
 *
 * @author huangdengfeng
 */
public class SysException extends BaseException {

    public final static int DEFAULT_ERR_CODE = -2;
    
    private static final long serialVersionUID = 1L;

    public SysException(String msg) {
        super(DEFAULT_ERR_CODE, msg);
    }

    public SysException(int code, String msg) {
        super(code, msg);
    }

    public SysException(String msg, Throwable e) {
        super(DEFAULT_ERR_CODE, msg, e);
    }

    public SysException(int code, String msg, Throwable e) {
        super(code, msg, e);
    }

}
