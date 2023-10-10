package com.seezoon.infrastructure.exception;

/**
 * BizException is known Exception, no need retry
 *
 * @author huangdengfeng
 */
public class BizException extends BaseException {

    public final static int DEFAULT_ERR_CODE = -1;
    private static final long serialVersionUID = 1L;

    public BizException(String msg) {
        super(DEFAULT_ERR_CODE, msg);
    }

    public BizException(int code, String msg) {
        super(code, msg);
    }

    public BizException(String msg, Throwable e) {
        super(DEFAULT_ERR_CODE, msg, e);
    }

    public BizException(int code, String msg, Throwable e) {
        super(code, msg, e);
    }

}