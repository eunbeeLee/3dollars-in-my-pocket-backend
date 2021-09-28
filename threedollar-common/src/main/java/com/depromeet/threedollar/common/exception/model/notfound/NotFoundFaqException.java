package com.depromeet.threedollar.common.exception.model.notfound;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.model.NotFoundException;

public class NotFoundFaqException extends NotFoundException {

    public NotFoundFaqException(String message) {
        super(message, ErrorCode.NOT_FOUND_FAQ_EXCEPTION);
    }

}
