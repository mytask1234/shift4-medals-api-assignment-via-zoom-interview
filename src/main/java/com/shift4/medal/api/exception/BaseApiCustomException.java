package com.shift4.medal.api.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseApiCustomException extends RuntimeException implements ApiExceptionData {

    private static final long serialVersionUID = 4086309836181430884L;

    protected final Object[] args; // in case we want to customize the responseMessage (by override the getResponseMessage() method), in the actual Exception class.
    protected final ApiCustomExcepMsgEnum errorEnum; // in case we want to customize the responseMessage (by override the getResponseMessage() method), in the actual Exception class.

    protected BaseApiCustomException(ApiCustomExcepMsgEnum errorEnum, Object... args) {
        super(String.format(errorEnum.getMessage(), args));

        this.errorEnum = errorEnum;
        this.args = args;
    }

    protected BaseApiCustomException(ApiCustomExcepMsgEnum errorEnum, Throwable cause, Object... args) {
        super(errorEnum.getMessage(), cause);

        this.errorEnum = errorEnum;
        this.args = args;
    }

    @Override
    public String getErrorCode() {
        return errorEnum.getErrorCode();
    }

    @Override
    public HttpStatus getStatus() {
        return errorEnum.getStatus();
    }

    @Override
    public String getError() {
        return errorEnum.getStatus().getReasonPhrase();
    }

    @Override
    public String getResponseMessage() {
        if (this.errorEnum.getResponseMessage() == null) {
            return this.getMessage(); // returns the default message
        } else {
            return this.errorEnum.getResponseMessage();
        }
    }
}
