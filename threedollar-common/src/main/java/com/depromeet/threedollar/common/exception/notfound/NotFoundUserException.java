package com.depromeet.threedollar.common.exception.notfound;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.NotFoundException;

public class NotFoundUserException extends NotFoundException {

    public NotFoundUserException(String message) {
        super(message, ErrorCode.NOT_FOUND_USER_EXCEPTION);
    }

}
