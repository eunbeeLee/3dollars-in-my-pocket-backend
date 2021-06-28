package com.depromeet.threedollar.domain.exception;

public class ConflictException extends BaseException {

	public ConflictException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public ConflictException(String message) {
		super(message, ErrorCode.CONFLICT_EXCEPTION);
	}

}
