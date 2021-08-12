package com.depromeet.threedollar.common.exception.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.ValidationException;

public class ValidationRatingException extends ValidationException {

    public ValidationRatingException(String message) {
        super(message, ErrorCode.VALIDATION_RATING_EXCEPTION);
    }

}
