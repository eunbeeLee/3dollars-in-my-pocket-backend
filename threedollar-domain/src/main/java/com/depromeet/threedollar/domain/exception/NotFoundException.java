package com.depromeet.threedollar.domain.exception;

public class NotFoundException extends BaseException{

	public NotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public NotFoundException(String message) {
		super(message, ErrorCode.NOT_FOUND_EXCEPTION);
	}

}
