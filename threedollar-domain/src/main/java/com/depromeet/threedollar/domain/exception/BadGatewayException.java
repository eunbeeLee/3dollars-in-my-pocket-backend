package com.depromeet.threedollar.domain.exception;

public class BadGatewayException extends BaseException {

    public BadGatewayException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public BadGatewayException(String message) {
        super(message, ErrorCode.BAD_GATEWAY_EXCEPTION);
    }

}
