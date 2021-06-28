package com.depromeet.threedollar.domain.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreator {

	public static User create(String socialId, UserSocialType socialType, String name) {
		return User.builder()
				.socialId(socialId)
				.socialType(socialType)
				.name(name)
				.statusType(UserStatusType.ACTIVE)
				.build();
	}

	public static User createInactive(String socialId, UserSocialType socialType, String name) {
		return User.builder()
				.socialId(socialId)
				.socialType(socialType)
				.name(name)
				.statusType(UserStatusType.INACTIVE)
				.build();
	}

}
