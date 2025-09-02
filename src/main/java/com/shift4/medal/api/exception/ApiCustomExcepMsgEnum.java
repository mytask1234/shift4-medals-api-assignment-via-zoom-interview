package com.shift4.medal.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiCustomExcepMsgEnum implements ApiExceptionData {

    ERROR_DEFAULT("Oops!!! Something went wrong.", "Oops!!! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR, null),

    // Medal errors
    ERROR_01045("A medal of type '%s' is not supported.", null, HttpStatus.BAD_REQUEST, UnsupportedMedalException.class);

    private String errorCode;
    private String message;
    private String responseMessage;
    private HttpStatus status;
    private Class<? extends BaseApiCustomException> exClass;

    ApiCustomExcepMsgEnum(String message, String responseMessage, HttpStatus status, Class<? extends BaseApiCustomException> exClass) {

        if (message == null) {
            throw new IllegalArgumentException("message can not be null");
        }

        this.errorCode = getErrorCode(this.name());
        this.message = message;
        this.responseMessage = responseMessage;
        this.status = status;
        this.exClass = exClass;
    }

    private String getErrorCode(String enumName) {
        
        if (enumName.equals("ERROR_DEFAULT")) {
            return "01000";
        }
        
        return enumName.substring(6);
    }
    
    @Override
    public String getError() {
        return this.getStatus().getReasonPhrase();
    }

    private static final Map<Class<? extends BaseApiCustomException>, ApiCustomExcepMsgEnum> exClassToErrorEnumMap;

    static {

        final Map<Class<? extends BaseApiCustomException>, ApiCustomExcepMsgEnum> tmpMap = new HashMap<>();

        for (ApiCustomExcepMsgEnum errorEnum : ApiCustomExcepMsgEnum.values()) { 

            if (errorEnum.getExClass() != null) {

                tmpMap.put(errorEnum.getExClass(), errorEnum);
            }
        }

        exClassToErrorEnumMap = Map.copyOf(tmpMap); // Returns an unmodifiable Map containing the entries of the given Map.
    }

    public static ApiCustomExcepMsgEnum getApiCustomExcepMsgEnum(Class<? extends BaseApiCustomException> exClass) {

        return exClassToErrorEnumMap.get(exClass);
    }
}
