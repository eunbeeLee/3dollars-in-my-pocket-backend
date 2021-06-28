package com.depromeet.threedollar.api.service.user.dto.request;

import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserRequest {

	@NotBlank
	private String socialId;

	@NotNull
	private UserSocialType socialType;

	@NotBlank
	private String name;

	public static CreateUserRequest of(String socialId, UserSocialType type, String name) {
		return new CreateUserRequest(socialId, type, name);
	}

	public static CreateUserRequest testInstance(String socialId, UserSocialType type, String name) {
		return new CreateUserRequest(socialId, type, name);
	}

	public User toEntity() {
		return User.newInstance(socialId, socialType, name);
	}

}
