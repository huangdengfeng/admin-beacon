package com.seezoon.infrastructure.configuration.advice;

import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.BizException;
import com.seezoon.infrastructure.exception.SysException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;

/**
 * 统一异常处理，将异常转化为错误码错误信息
 *
 * @author huangdengfeng
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionAdvice {

    /**
     * for Receiving parameters and verification
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, ValidationException.class,
            MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, BindException.class})
    public Response parameterInvalidException(Exception e) {
        log.error("parameter invalid exception", e);
        return Response.error(ErrorCode.PARAM_INVALID.code(),
                String.format(ErrorCode.PARAM_INVALID.msg(), e.getMessage()));
    }

    /**
     * for using {@link org.springframework.util.Assert}
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public Response illegalArgumentException(IllegalArgumentException e) {
        log.error("illegal argument exception", e);
        return Response.error(ErrorCode.PARAM_INVALID.code(),
                String.format(ErrorCode.PARAM_INVALID.msg(), e.getMessage()));
    }

    @ExceptionHandler({SQLException.class, TransactionException.class})
    public Response sqlException(Exception e) {
        log.error("sql exception", e);
        return Response.error(ErrorCode.SQL_ERROR.code(), String.format(ErrorCode.SQL_ERROR.msg(), e.getMessage()));
    }

    @ExceptionHandler(BizException.class)
    public Response bizException(BizException e) {
        return Response.error(e.getcode(), e.getMessage());
    }

    @ExceptionHandler(SysException.class)
    public Response sysException(SysException e) {
        return Response.error(e.getcode(), e.getMessage());
    }

    /**
     * 无权限时候使用
     *
     * @param response
     * @param e
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(HttpServletResponse response, AccessDeniedException e) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response uploadException(MaxUploadSizeExceededException e) {
        log.error("upload exception {}", e.getMessage());
        return Response.error(ErrorCode.FILE_SIZE_INVALID.code(), ErrorCode.FILE_SIZE_INVALID.msg());
    }

    /**
     * using this if there is no match.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.error("unspecified exception", e);
        return Response.error(ErrorCode.UNKNOWN.code(), String.format(ErrorCode.UNKNOWN.msg(), e.getMessage()));
    }
}
