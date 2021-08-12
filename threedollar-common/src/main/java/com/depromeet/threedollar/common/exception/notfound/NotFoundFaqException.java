package com.depromeet.threedollar.common.exception.notfound;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.NotFoundException;

public class NotFoundFaqException extends NotFoundException {

    public NotFoundFaqException(String message) {
        super(message, ErrorCode.NOT_FOUND_FAQ_EXCEPTION);
    }

}
