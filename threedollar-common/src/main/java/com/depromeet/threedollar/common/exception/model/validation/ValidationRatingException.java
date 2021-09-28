package com.depromeet.threedollar.common.exception.model.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.model.ValidationException;

public class ValidationRatingException extends ValidationException {

    public ValidationRatingException(String message) {
        super(message, ErrorCode.VALIDATION_RATING_EXCEPTION);
    }

}
