package com.depromeet.threedollar.domain.exception;

public class ForbiddenException extends BaseException {

	public ForbiddenException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public ForbiddenException(String message) {
		super(message, ErrorCode.FORBIDDEN_EXCEPTION);
	}

}
