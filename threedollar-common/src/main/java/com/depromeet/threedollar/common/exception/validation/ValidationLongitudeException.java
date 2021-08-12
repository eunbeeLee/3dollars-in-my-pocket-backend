package com.depromeet.threedollar.common.exception.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.ValidationException;

public class ValidationLongitudeException extends ValidationException {

    public ValidationLongitudeException(String message) {
        super(message, ErrorCode.VALIDATION_LONGITUDE_EXCEPTION);
    }

}
