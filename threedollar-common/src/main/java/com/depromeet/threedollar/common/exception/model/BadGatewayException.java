package com.depromeet.threedollar.common.exception.model;

import static com.depromeet.threedollar.common.exception.ErrorCode.BAD_GATEWAY_EXCEPTION;

public class BadGatewayException extends ThreeDollarsBaseException {

    public BadGatewayException(String message) {
        super(message, BAD_GATEWAY_EXCEPTION);
    }

}
