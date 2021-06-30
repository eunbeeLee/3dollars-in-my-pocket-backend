package com.depromeet.threedollar.api.service.auth.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

	private String token;

	public static LoginResponse of(String token) {
		return new LoginResponse(token);
	}

}
