package com.depromeet.threedollar.api.service.auth.dto.request;

import com.depromeet.threedollar.api.service.user.dto.request.CreateUserRequest;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

	@NotBlank
	private String token;

	@NotBlank
	private String name;

	public static SignUpRequest testInstance(String token, String name) {
		return new SignUpRequest(token, name);
	}

	public CreateUserRequest toCreateUserRequest(String socialId, UserSocialType socialType) {
		return CreateUserRequest.of(socialId, socialType, name);
	}

}
