package com.depromeet.threedollar.common.exception;

public class HttpVersionNotSupportedException extends BaseException {

    public HttpVersionNotSupportedException(String message) {
        super(message, ErrorCode.HTTP_VERSION_NOT_SUPPORTED_EXCEPTION);
    }

}
