package com.depromeet.threedollar.api.service.auth;

import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.threedollar.api.service.user.UserService;
import com.depromeet.threedollar.common.exception.model.NotFoundException;
import com.depromeet.threedollar.domain.domain.user.*;
import com.depromeet.threedollar.external.client.kakao.KaKaoApiClient;
import com.depromeet.threedollar.external.client.kakao.dto.response.KaKaoProfileResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    @Autowired
    private WithdrawalUserRepository withdrawalUserRepository;

    @BeforeEach
    void setUp() {
        authService = new KaKaoAuthService(new StubKaKaoApiCaller(), userService, userRepository);
    }

    @AfterEach
    void cleanUp() {
        withdrawalUserRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Nested
    class 카카오_로그인 {

        @Test
        void 성공시_멤버의_식별키가_반환된다() {
            // given
            User user = UserCreator.create(socialId, UserSocialType.KAKAO, "닉네임");
            userRepository.save(user);

            LoginRequest request = LoginRequest.testInstance("token", UserSocialType.KAKAO);

            // when
            Long userId = authService.login(request);

            // then
            assertThat(userId).isEqualTo(user.getId());
        }

        @Test
        void 가입한_유저가_아니면_NOT_FOUND_USER_EXCEPTION() {
            // given
            LoginRequest request = LoginRequest.testInstance("token", UserSocialType.KAKAO);

            // when & then
            assertThatThrownBy(() -> authService.login(request)).isInstanceOf(NotFoundException.class);
        }

    }

    @Nested
    class 카카오_회원가입 {

        @Test
        void 성공시_새로운_유저정보가_저장된다() {
            // given
            SignUpRequest request = SignUpRequest.testInstance("token", "가슴속 삼천원", UserSocialType.KAKAO);

            // when
            authService.signUp(request);

            // then
            List<User> users = userRepository.findAll();
            assertThat(users).hasSize(1);
            assertUser(users.get(0), socialId, request.getName(), request.getSocialType());
        }

    }

    private static class StubKaKaoApiCaller implements KaKaoApiClient {

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
