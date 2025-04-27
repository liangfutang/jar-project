package com.projects.common.exception;


import com.projects.common.code.ResultCodeEnum;
import com.projects.common.code.ServiceCode;

/**
 * 用户会话缺少租户异常
 * @author tlf
 */
public class NoTenantException extends RuntimeException {

    private Integer code = ResultCodeEnum.NO_TENANT.getCode();

    public NoTenantException() {
        super(ResultCodeEnum.NO_TENANT.getMessage());
    }

    public NoTenantException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public NoTenantException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public NoTenantException(ServiceCode serviceCode) {
        super(serviceCode.getMessage());
        this.code = serviceCode.getCode();
    }
    public NoTenantException(String message) {
        super(message);
    }
    public NoTenantException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public NoTenantException(Throwable throwable) {
        super(throwable);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
