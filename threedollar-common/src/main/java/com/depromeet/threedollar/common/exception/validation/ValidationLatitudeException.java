package com.depromeet.threedollar.common.exception.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.ValidationException;

public class ValidationLatitudeException extends ValidationException {

    public ValidationLatitudeException(String message) {
        super(message, ErrorCode.VALIDATION_LATITUDE_EXCEPTION);
    }

}
