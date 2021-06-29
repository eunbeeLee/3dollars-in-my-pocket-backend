package com.depromeet.threedollar.api.service.auth;

import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.threedollar.api.service.user.UserService;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserCreator;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import com.depromeet.threedollar.domain.exception.NotFoundException;
import com.depromeet.threedollar.external.external.auth.kakao.KaKaoApiCaller;
import com.depromeet.threedollar.external.external.auth.kakao.dto.response.KaKaoProfileResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class KaKaoAuthServiceTest {

    private static final String socialId = "social-id";

    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        authService = new KaKaoAuthService(new StubKaKaoApiCaller(), userService, userRepository);
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void 카카오_로그인_요청시_회원가입한_유저면_멤버의_PK_가_반환된다() {
        // given
        User user = UserCreator.create(socialId, UserSocialType.KAKAO, "닉네임");
        userRepository.save(user);

        LoginRequest request = LoginRequest.testInstance("token");

        // when
        Long userId = authService.login(request);

        // then
        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    void 카카오_로그인_요청시_아직_회원가입하지_않은_유저면_404_에러가_발생한다() {
        // given
        LoginRequest request = LoginRequest.testInstance("token");

        // when & then
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 새로운_유저가_카카오로_회원가입시_회원정보가_DB_에_저장된다() {
        // given
        String name = "무야호";
        UserSocialType socialType = UserSocialType.KAKAO;

        SignUpRequest request = SignUpRequest.testInstance("token", name);

        authService.signUp(request);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertUser(users.get(0), socialId, name, socialType);
    }

    private static class StubKaKaoApiCaller implements KaKaoApiCaller {
        @Override
        public KaKaoProfileResponse getProfileInfo(String accessToken) {
            return KaKaoProfileResponse.testInstance(socialId);
        }
    }

    private void assertUser(User user, String socialId, String name, UserSocialType socialType) {
        assertThat(user.getSocialId()).isEqualTo(socialId);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSocialType()).isEqualTo(socialType);
    }

}
