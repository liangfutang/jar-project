package com.project.framework.common.exception;

import com.project.framework.common.code.ResultCodeEnum;
import com.project.framework.common.code.ServiceCode;

/**
 * 用户不存在
 * @author tlf
 */
public class NoAccountException extends RuntimeException {

    private Integer code = ResultCodeEnum.NO_ACCOUNT.getCode();

    public NoAccountException() {
        super(ResultCodeEnum.NO_ACCOUNT.getMessage());
    }

    public NoAccountException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public NoAccountException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public NoAccountException(ServiceCode serviceCode) {
        super(serviceCode.getMessage());
        this.code = serviceCode.getCode();
    }
    public NoAccountException(String message) {
        super(message);
    }
    public NoAccountException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public NoAccountException(Throwable throwable) {
        super(throwable);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
