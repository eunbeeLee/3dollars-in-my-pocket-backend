package com.depromeet.threedollar.common.exception.model.notfound;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.model.NotFoundException;

public class NotFoundStoreException extends NotFoundException {

    public NotFoundStoreException(String message) {
        super(message, ErrorCode.NOT_FOUND_STORE_EXCEPTION);
    }

}
