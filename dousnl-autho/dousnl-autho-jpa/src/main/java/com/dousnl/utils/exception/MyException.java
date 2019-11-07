package com.dousnl.utils.exception;

import com.dousnl.utils.enums.ErrorEnums;

/**
 * 自定义异常
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/4 14:25
 */
public class MyException extends RuntimeException{

    private String errorCode;

    private String message;

    public MyException() {
    }

    public MyException(ErrorEnums enums){
        this.errorCode=enums.getCode();
        this.message=enums.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
