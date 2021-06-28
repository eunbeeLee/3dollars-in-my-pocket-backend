package com.depromeet.threedollar.domain.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

	// Common
	VALIDATION_EXCEPTION("C001", "잘못된 요청입니다"),
	UNAUTHORIZED_EXCEPTION("C002", "세션이 만료되었습니다. 다시 로그인 해주세요"),
	FORBIDDEN_EXCEPTION("C003", "허용하지 않는 접근입니다."),
	NOT_FOUND_EXCEPTION("C004", "존재하지 않습니다"),
	CONFLICT_EXCEPTION("C005", "이미 존재합니다"),
	INTERNAL_SERVER_EXCEPTION("C006", "서버 내부에서 에러가 발생하였습니다"),
	METHOD_NOT_ALLOWED_EXCEPTION("C007", "지원하지 않는 메소드 입니다"),
	BAD_GATEWAY_EXCEPTION("C008", "외부 연동 중 에러가 발생하였습니다"),
    SERVICE_UNAVAILABLE_EXCEPTION("C009", "서비스를 이용하실 수 없습니다")
	;

	private final String code;
	private final String message;

}
