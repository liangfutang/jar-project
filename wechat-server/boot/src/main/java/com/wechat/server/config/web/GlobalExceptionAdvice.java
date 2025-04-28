package com.wechat.server.config.web;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.project.framework.common.code.ResultCodeEnum;
import com.project.framework.common.result.Result;
import com.project.framework.common.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 * 全局异常处理
 *
 * @author Ji MingHao
 * @since 2022-12-01 15:57
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler({DataIntegrityViolationException.class, BadSqlGrammarException.class})
    public Result<?> sqlExceptionHandler(Exception ex) {
        log.warn("操作数据库异常:{}", ex.getMessage());
        return Results.failure(ResultCodeEnum.WRITE_ERROR.getCode(), ex.getCause().getMessage());
    }

    /**
     * 运行时异常处理
     *
     * @param ex 异常
     * @return 错误信息
     */
    @ExceptionHandler({RuntimeException.class})
    public <T> Result<T> runtimeExceptionHandler(Exception ex) {
        String errorMsg = ResultCodeEnum.SYS_FAILURE.getMessage();
        if (ex instanceof RecoverableDataAccessException) {
            RecoverableDataAccessException exception = (RecoverableDataAccessException) ex;
            Throwable cause = exception.getCause();
            errorMsg = cause instanceof CommunicationsException ? "请求超时(网络波动),请稍后重试" : "数据库查询失败";
        } else {
            errorMsg = ex.getMessage() == null ? errorMsg : ex.getMessage();
        }
        ex.printStackTrace();
        return Results.failure(ResultCodeEnum.SYS_FAILURE.getCode(), errorMsg);
    }

    /**
     * 异常处理
     *
     * @param ex 异常
     * @return 错误信息
     */
    @ExceptionHandler({Exception.class})
    public <T> Result<T> exceptionHandler(Exception ex) {
        String errorMsg = StringUtils.isBlank(ex.getMessage()) ? ResultCodeEnum.FAILURE.message() : ex.getMessage();
        ex.printStackTrace();
        return Results.failure(500, errorMsg);
    }
}
