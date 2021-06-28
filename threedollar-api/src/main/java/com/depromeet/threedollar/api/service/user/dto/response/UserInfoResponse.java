package com.depromeet.threedollar.api.service.user.dto.response;

import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

	private Long id;

	private String name;

	private UserSocialType socialType;

	public static UserInfoResponse of(User user) {
		return new UserInfoResponse(user.getId(), user.getName(), user.getSocialType());
	}

	public static UserInfoResponse of(Long userId, String userName, UserSocialType userSocialType) {
		return new UserInfoResponse(userId, userName, userSocialType);
	}

}
