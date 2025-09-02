package com.shift4.medal.api.exception;

import org.springframework.http.HttpStatus;

public interface ApiExceptionData {

    String getErrorCode();

    HttpStatus getStatus();

    String getError();

    String getResponseMessage();
}
