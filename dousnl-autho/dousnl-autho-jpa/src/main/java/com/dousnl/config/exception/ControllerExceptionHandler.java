package com.dousnl.config.exception;

import com.dousnl.utils.enums.ErrorEnums;
import com.dousnl.utils.exception.MyException;
import com.dousnl.utils.response.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 通用异常返回信息
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/4 14:22
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(value = MyException.class)
    public Resp myErrorHandler(MyException ex){
        log.error("异常信息：" + ex.getMessage(), ex);
        return Resp.failed(ex.getErrorCode(),ex.getMessage());
    }
    /**
     * 默认的异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp exceptionHandler(Exception ex) {
        log.error("异常信息：" + ex.getMessage(), ex);
        return Resp.failed(ErrorEnums.SYSTEM_EXCEPTION.getCode(),ErrorEnums.SYSTEM_EXCEPTION.getMessage());
    }
}
