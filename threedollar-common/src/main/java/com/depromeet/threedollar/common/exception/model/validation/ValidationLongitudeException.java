package com.depromeet.threedollar.common.exception.model.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.model.ValidationException;

public class ValidationLongitudeException extends ValidationException {

    public ValidationLongitudeException(String message) {
        super(message, ErrorCode.VALIDATION_LONGITUDE_EXCEPTION);
    }

}
