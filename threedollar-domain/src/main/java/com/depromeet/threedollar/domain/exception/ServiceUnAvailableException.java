package com.depromeet.threedollar.domain.exception;

public class ServiceUnAvailableException extends BaseException {

	public ServiceUnAvailableException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public ServiceUnAvailableException(String message) {
		super(message, ErrorCode.SERVICE_UNAVAILABLE_EXCEPTION);
	}

}
