package com.depromeet.threedollar.common.exception.model.validation;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.model.ValidationException;

public class ValidationFileTypeException extends ValidationException {

    public ValidationFileTypeException(String message) {
        super(message, ErrorCode.VALIDATION_FILE_TYPE_EXCEPTION);
    }

}
