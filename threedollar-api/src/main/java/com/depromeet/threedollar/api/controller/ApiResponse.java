package com.depromeet.threedollar.api.controller;

import com.depromeet.threedollar.domain.exception.ErrorCode;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	public static final ApiResponse<String> SUCCESS = success("OK");

	private String resultCode;

	private String message;

	private T data;

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>("", "", data);
	}

	public static <T> ApiResponse<T> error(ErrorCode errorCode) {
		return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
	}

	public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
		return new ApiResponse<>(errorCode.getCode(), message, null);
	}

}
