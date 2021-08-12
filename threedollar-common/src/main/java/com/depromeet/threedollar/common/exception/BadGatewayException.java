package com.depromeet.threedollar.common.exception;

import com.depromeet.threedollar.common.exception.BaseException;

import static com.depromeet.threedollar.common.exception.ErrorCode.BAD_GATEWAY_EXCEPTION;

public class BadGatewayException extends BaseException {

    public BadGatewayException(String message) {
        super(message, BAD_GATEWAY_EXCEPTION);
    }

}
