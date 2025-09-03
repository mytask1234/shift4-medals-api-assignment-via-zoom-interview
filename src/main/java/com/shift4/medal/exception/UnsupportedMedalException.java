package com.shift4.medal.api.exception;

import static com.shift4.medal.api.exception.ApiCustomExcepMsgEnum.getApiCustomExcepMsgEnum;

public class UnsupportedMedalException extends BaseApiCustomException {

    private static final long serialVersionUID = -6549958764447414906L;

    public UnsupportedMedalException(Object... args) {
        super(getApiCustomExcepMsgEnum(UnsupportedMedalException.class), args);
    }
}
