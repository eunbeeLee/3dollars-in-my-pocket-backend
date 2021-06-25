package com.depromeet.threedollar.domain.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreator {

	public static User create(String socialId, UserSocialType socialType, String name) {
		return new User(socialId, socialType, name);
	}

}
