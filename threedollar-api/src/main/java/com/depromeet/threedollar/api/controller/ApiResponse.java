package com.depromeet.threedollar.api.controller;

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

}
