package com.depromeet.threedollar.domain.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SocialInfoTest {

    @Test
    void 유저_소셜정보_동등성_테스트_모두_같으면_같은_객체로_판단() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;

        // when
        SocialInfo source = SocialInfo.of(socialId, type);
        SocialInfo target = SocialInfo.of(socialId, type);

        // then
        assertThat(source).isEqualTo(target);
    }

    @Test
    void 유저_소셜정보_동등성_테스트_하나라도_다른경우_다른_객체로_판단() {
        // given
        String socialId = "social-id";

        // when
        SocialInfo source = SocialInfo.of(socialId, UserSocialType.APPLE);
        SocialInfo target = SocialInfo.of(socialId, UserSocialType.KAKAO);

        // then
        assertThat(source).isNotEqualTo(target);
    }

}
