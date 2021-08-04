package com.depromeet.threedollar.common.exception;

import static com.depromeet.threedollar.common.exception.ErrorCode.BAD_GATEWAY_EXCEPTION;

public class BadGatewayException extends BaseException {

    public BadGatewayException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public BadGatewayException(String message) {
        super(message, BAD_GATEWAY_EXCEPTION);
    }

}
