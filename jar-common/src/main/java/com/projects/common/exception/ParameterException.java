package com.projects.common.exception;


import com.projects.common.code.ResultCodeEnum;
import com.projects.common.code.ServiceCode;

/**
 * 参数异常
 * @author tlf
 */
public class ParameterException extends RuntimeException {

    private Integer code = ResultCodeEnum.PARAMETER_ILLEGAL.getCode();

    public ParameterException() {
        super(ResultCodeEnum.PARAMETER_ILLEGAL.getMessage());
    }

    public ParameterException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public ParameterException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public ParameterException(ServiceCode serviceCode) {
        super(serviceCode.getMessage());
        this.code = serviceCode.getCode();
    }
    public ParameterException(String message) {
        super(message);
    }
    public ParameterException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public ParameterException(Throwable throwable) {
        super(throwable);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
