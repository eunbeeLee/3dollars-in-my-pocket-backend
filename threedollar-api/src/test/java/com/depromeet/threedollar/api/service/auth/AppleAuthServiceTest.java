package com.depromeet.threedollar.api.service.auth;

import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.threedollar.api.service.user.UserService;
import com.depromeet.threedollar.domain.domain.user.*;
import com.depromeet.threedollar.common.exception.NotFoundException;
import com.depromeet.threedollar.external.external.auth.apple.AppleTokenDecoder;
import com.depromeet.threedollar.external.external.auth.apple.dto.response.IdTokenPayload;
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
class AppleAuthServiceTest {

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
        authService = new AppleAuthService(new StubAppleTokenDecoder(), userRepository, userService);
    }

    @AfterEach
    void cleanUp() {
        withdrawalUserRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Nested
    class 애플_로그인 {

        @Test
        void 성공시_멤버의_PK_가_반환된다() {
            // given
            User user = UserCreator.create(socialId, UserSocialType.APPLE, "닉네임");
            userRepository.save(user);

            LoginRequest request = LoginRequest.testInstance("token", UserSocialType.APPLE);

            // when
            Long userId = authService.login(request);

            // then
            assertThat(userId).isEqualTo(user.getId());
        }

        @Test
        void 가입한_유저가_아니면_NOT_FOUND_EXCEPTION() {
            // given
            LoginRequest request = LoginRequest.testInstance("token", UserSocialType.APPLE);

            // when & then
            assertThatThrownBy(() -> authService.login(request)).isInstanceOf(NotFoundException.class);
        }

    }

    @Nested
    class 애플_회원가입 {

        @Test
        void 성공시_새로운_유저정보가_저장된다() {
            // given
            SignUpRequest request = SignUpRequest.testInstance("token", "가슴속 삼천원", UserSocialType.APPLE);

            authService.signUp(request);

            // then
            List<User> users = userRepository.findAll();
            assertThat(users).hasSize(1);
            assertUser(users.get(0), socialId, request.getName(), request.getSocialType());
        }

    }

    @Nested
    class 애플_회원탈퇴 {

        @Test
        void 성공시_해당하는_유저정보가_삭제된다() {
            // given
            User user = UserCreator.create(socialId, UserSocialType.APPLE, "닉네임");
            userRepository.save(user);

            // when
            authService.signOut(user.getId());

            // then
            List<User> users = userRepository.findAll();
            assertThat(users).isEmpty();
        }

        @Test
        void 카카오로_가입한_유저가_애플_회원탈퇴_요청시_NOT_FOUND_EXCEPTION() {
            // given
            User user = UserCreator.create(socialId, UserSocialType.KAKAO, "닉네임");
            userRepository.save(user);

            // when & then
            assertThatThrownBy(() -> authService.signOut(user.getId())).isInstanceOf(NotFoundException.class);
        }

    }

    private static class StubAppleTokenDecoder implements AppleTokenDecoder {
        @Override
        public IdTokenPayload getUserInfoFromToken(String idToken) {
            return IdTokenPayload.testInstance(socialId);
        }
    }

    private void assertUser(User user, String socialId, String name, UserSocialType socialType) {
        assertThat(user.getSocialId()).isEqualTo(socialId);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSocialType()).isEqualTo(socialType);
    }

}
