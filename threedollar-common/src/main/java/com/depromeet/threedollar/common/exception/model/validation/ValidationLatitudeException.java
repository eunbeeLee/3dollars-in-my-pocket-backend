package com.depromeet.threedollar.common.exception.model.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.model.ValidationException;

public class ValidationLatitudeException extends ValidationException {

    public ValidationLatitudeException(String message) {
        super(message, ErrorCode.VALIDATION_LATITUDE_EXCEPTION);
    }

}
