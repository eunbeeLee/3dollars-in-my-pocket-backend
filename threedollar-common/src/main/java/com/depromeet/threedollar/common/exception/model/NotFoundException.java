package com.depromeet.threedollar.common.exception.model;

import com.depromeet.threedollar.common.exception.ErrorCode;

public class NotFoundException extends ThreeDollarsBaseException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND_EXCEPTION);
    }

}
