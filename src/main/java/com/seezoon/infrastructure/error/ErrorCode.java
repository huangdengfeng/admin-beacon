package com.seezoon.infrastructure.error;


import com.seezoon.infrastructure.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {


    /**
     * 建议公共错误定义在1000-2000 的范围
     */
    UNKNOWN(1000, "system error, %s"),

    PARAM_INVALID(1001, "param invalid, %s"),

    SQL_ERROR(1002, "sql error", true),

    ASSERTION_ERROR(1003, "assertion error"),

    FILE_SIZE_INVALID(1004, "文件大小不合法"),

    RECORD_NOT_EXISTS(1005, "记录不存在"),

    FILE_UPLOAD_FAILED(1006, "文件上传失败:%s"),

    FILE_NOT_EXISTS(1007, "文件不存在"),

    /**
     * 业务错误
     */
    SYS_ADMIN_NOT_ALLOW_MODIFY(2000, "系统管理员禁止修改"),
    SYS_USER_NAME_EXISTS(2001, "用户名已存在"),
    USER_NOT_EXISTS(2002, "用户不存在"),
    USER_STATUS_INVALID(2003, "用户状态不正常"),
    USER_STATUS_LOCKED(2004, "用户已锁定，请联系管理员"),
    USER_PASSWD_WRONG(2005, "用户名或密码错误"),
    OLD_PASSWD_WRONG(2006, "原密码错误"),
    USER_AUTHORIZATION(2007, "获取认证信息失败，请稍后重试"),
    ORIGINAL_PASSWD_WRONG(2008, "原密码错误"),

    PERMISSION_CODE_EXISTS(2030, "权限编码已存在"),
    PERMISSION_PARENT_NOT_EXISTS(2031, "父权限编码不存在"),
    ROLE_CODE_EXISTS(2032, "角色编码已存在"),
    PERMISSION_LIST_ERROR(2033, "权限列表异常，请刷新权限列表后重新操作"),
    ROLE_LIST_ERROR(2034, "角色列表异常，请刷新角色列表后重新操作");


    public static final int ERROR_TYPE_BUSINESS = 0;
    public static final int ERROR_TYPE_SYSTEM = 1;


    private int code;
    private String msg;

    private int type;

    ErrorCode(int code, String msg) {
        this(code, msg, false);
    }

    ErrorCode(int code, String msg, boolean sysError) {
        this.code = code;
        this.msg = msg;
        this.type = sysError ? ERROR_TYPE_SYSTEM : ERROR_TYPE_BUSINESS;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public int type() {
        return type;
    }

    public boolean IsSystemError() {
        return type == ERROR_TYPE_SYSTEM;
    }
}
