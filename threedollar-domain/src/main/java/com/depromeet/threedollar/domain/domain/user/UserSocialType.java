package com.depromeet.threedollar.domain.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSocialType {

	KAKAO("카카오"),
	APPLE("애플");

	private final String type;

}
