package com.depromeet.threedollar.domain.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SocialInfoTest {

	@Test
	void SocialInfo_동등성_테스트() {
		// given
		String socialId = "social-id";
		UserSocialType type = UserSocialType.KAKAO;

		// when
		SocialInfo source = SocialInfo.of(socialId, type);
		SocialInfo target = SocialInfo.of(socialId, type);

		// then
		assertThat(source).isEqualTo(target);
	}

}
