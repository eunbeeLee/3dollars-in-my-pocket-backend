package com.depromeet.threedollar.common.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message) {
        super(message);
    }

}
