package com.project.framework.common.exception;

import com.project.framework.common.code.ResultCodeEnum;
import com.project.framework.common.code.ServiceCode;

/**
 * 用户没有登录异常
 * @author tlf
 */
public class NoLoginException extends RuntimeException {

    private Integer code = ResultCodeEnum.NOT_LOGIN.getCode();

    public NoLoginException() {
        super(ResultCodeEnum.NOT_LOGIN.getMessage());
    }

    public NoLoginException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public NoLoginException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public NoLoginException(ServiceCode serviceCode) {
        super(serviceCode.getMessage());
        this.code = serviceCode.getCode();
    }
    public NoLoginException(String message) {
        super(message);
    }
    public NoLoginException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public NoLoginException(Throwable throwable) {
        super(throwable);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
