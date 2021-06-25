package com.depromeet.threedollar.api.service.auth.dto.request;

import com.depromeet.threedollar.api.service.user.dto.request.CreateUserRequest;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

	@NotBlank
	private String token;

	@NotBlank
	private String name;

	public CreateUserRequest toCreateUserRequest(String socialId, UserSocialType socialType) {
		return CreateUserRequest.of(socialId, socialType, name);
	}

}
