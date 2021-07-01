package com.depromeet.threedollar.common.exception;

public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UnAuthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_EXCEPTION);
    }

}