package com.project.framework.common.exception;

import com.project.framework.common.code.ResultCodeEnum;
import com.project.framework.common.code.ServiceCode;

/**
 * 登录异常
 * @author tlf
 */
public class LoginException extends RuntimeException {

    private Integer code = ResultCodeEnum.LOGIN_ECECPTION.getCode();

    public LoginException() {
        super(ResultCodeEnum.LOGIN_ECECPTION.getMessage());
    }

    public LoginException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public LoginException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public LoginException(ServiceCode serviceCode) {
        super(serviceCode.getMessage());
        this.code = serviceCode.getCode();
    }
    public LoginException(String message) {
        super(message);
    }
    public LoginException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public LoginException(Throwable throwable) {
        super(throwable);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
