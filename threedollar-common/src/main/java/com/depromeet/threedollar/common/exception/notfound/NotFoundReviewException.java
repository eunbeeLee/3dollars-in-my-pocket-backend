package com.depromeet.threedollar.common.exception.notfound;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.NotFoundException;

public class NotFoundReviewException extends NotFoundException {

    public NotFoundReviewException(String message) {
        super(message, ErrorCode.NOT_FOUND_REVIEW_EXCEPTION);
    }

}
