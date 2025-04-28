package com.project.framework.common.code;

/**
 * 全局通用错误码信息
 *
 * @author tlf
 */
public enum ResultCodeEnum implements ServiceCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(500, "操作失败"),
    /**
     * 用户不存在
     */
    NO_ACCOUNT(400, "用户不存在"),
    /**
     * 没登录
     */
    NOT_LOGIN(401, "用户未登录"),
    /**
     * 登录异常
     */
    LOGIN_ECECPTION(402, "登录异常"),
    /**
     * 没有操作的权限
     */
    NO_AUTH(450, "没有权限的请求"),
    /**
     * 缺少租户信息
     */
    NO_TENANT(470, "没有请求的租户信息"),

    /**
     * 请求参数不合法
     */
    PARAMETER_ILLEGAL(2000, "请求参数不合法"),

    /**
     * 缺少必要的参数
     */
    NO_INPUT_PARAMETER_EXCEPTION(2002, "缺少必要的参数"),

    /**
     * 服务内部错误
     */
    INTERNAL_SERVER_ERROR(5000, "服务内部错误"),

    /**
     * 系统异常
     */
    SYS_FAILURE(1000, "未知系统异常"),

    /**
     * 业务异常
     */
    COMMON_FAILURE(3000, "未知操作异常"),

    /**
     * 读取redis异常
     */
    REDIS_READ_ERROR(4000, "读取redis异常"),

    /**
     * 写redis异常
     */
    REDIS_WRITE_ERROR(4001, "写redis异常"),
    /**
     * 读取mysql异常
     */
    MYSQL_READ_ERROR(4100, "读取mysql异常"),
    /**
     * 写redis异常
     */
    MYSQL_WRITE_ERROR(4101, "写mysql异常"),
    /**
     * 读取TDengine异常
     */
    TD_READ_ERROR(4200, "读取TDengine异常"),
    /**
     * 写TDengine异常
     */
    TD_WRITE_ERROR(4201, "写TDengine异常"),
    /**
     * 读数据库异常
     */
    READ_ERROR(4500, "读取数据库异常"),
    /**
     * 写数据库异常
     */
    WRITE_ERROR(4501, "写数据库异常");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
