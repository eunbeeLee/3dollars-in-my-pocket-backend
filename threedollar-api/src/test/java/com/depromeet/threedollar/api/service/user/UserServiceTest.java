package com.depromeet.threedollar.api.service.user;

import com.depromeet.threedollar.api.service.user.dto.request.CreateUserRequest;
import com.depromeet.threedollar.api.service.user.dto.request.UpdateUserInfoRequest;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.user.*;
import com.depromeet.threedollar.common.exception.ConflictException;
import com.depromeet.threedollar.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WithdrawalUserRepository withdrawalUserRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        withdrawalUserRepository.deleteAll();
    }

    @Test
    void 새로운_유저가_회원가입하면_DB에_유저정보가_저장된다() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;
        String name = "가슴속 삼천원";

        CreateUserRequest request = CreateUserRequest.testInstance(socialId, type, name);

        // when
        userService.createUser(request);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertUserInfo(users.get(0), socialId, type, name, UserStatusType.ACTIVE);
    }

    @Test
    void 이미_존재하는_닉네임인_경우_에러가_발생한다() {
        // given
        String name = "가슴속 삼천원";
        userRepository.save(UserCreator.create("social-id", UserSocialType.KAKAO, name));

        CreateUserRequest request = CreateUserRequest.testInstance("another-id", UserSocialType.APPLE, name);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원탈퇴한_유저의_닉네임과_중복될수_없다() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;
        String name = "가슴속 삼천원";
        userRepository.save(UserCreator.createInactive("social-id", type, name));

        CreateUserRequest request = CreateUserRequest.testInstance("another-id", UserSocialType.APPLE, name);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 이미_존재하는_소셜_ID인경우_에러가_발생한다() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;
        userRepository.save(UserCreator.create(socialId, type, "기존의 닉네임"));

        CreateUserRequest request = CreateUserRequest.testInstance(socialId, type, "새로운 닉네임");

        // when & then
        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 소셜ID가_같더라도_제공하는_소셜이_다른경우_회원가입이_정상적으로_처리된다() {
        // given
        String socialId = "social-id";
        userRepository.save(UserCreator.create(socialId, UserSocialType.APPLE, "기존의 닉네임"));

        CreateUserRequest request = CreateUserRequest.testInstance(socialId, UserSocialType.KAKAO, "새로운 닉네임");

        // when
        userService.createUser(request);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertUserInfo(users.get(0), socialId, UserSocialType.APPLE, "기존의 닉네임", UserStatusType.ACTIVE);
        assertUserInfo(users.get(1), socialId, UserSocialType.KAKAO, "새로운 닉네임", UserStatusType.ACTIVE);
    }

    @Test
    void 유저_정보를_조회한다() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;
        String name = "가슴속 삼천원";
        User user = UserCreator.create(socialId, type, name);
        userRepository.save(user);

        // when
        UserInfoResponse response = userService.getUserInfo(user.getId());

        // then
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getSocialType()).isEqualTo(type);
    }

    @Test
    void 존재하지_않는_유저정보를_조회하면_에러가_발생한다() {
        // given
        Long userId = 999L;

        // when & then
        assertThatThrownBy(() -> userService.getUserInfo(userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 유저정보를_수정요청하면_DB_정보가_변경된다() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;
        String name = "가슴속 삼천원";
        User user = UserCreator.create(socialId, type, "기존의 닉네임");
        userRepository.save(user);

        UpdateUserInfoRequest request = UpdateUserInfoRequest.testInstance(name);

        // when
        userService.updateUserInfo(request, user.getId());

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertUserInfo(users.get(0), socialId, type, name, UserStatusType.ACTIVE);
    }

    @Test
    void 존재하지_않는_유저정보를_수정요청하면_에러가_발생한다() {
        // given
        Long userId = 999L;
        UpdateUserInfoRequest request = UpdateUserInfoRequest.testInstance("name");

        // when & then
        assertThatThrownBy(() -> userService.updateUserInfo(request, userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 회원탈퇴하면_INACTIVE되고_WithdrawlUser_데이터가_하나_생성된다() {
        // given
        User user = UserCreator.create("social-id", UserSocialType.APPLE, "기존의 닉네임");
        userRepository.save(user);

        // then
        userService.signOut(user.getId());

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getStatus()).isEqualTo(UserStatusType.INACTIVE);

        // then
        List<WithdrawalUser> withdrawalUsers = withdrawalUserRepository.findAll();
        assertThat(withdrawalUsers).hasSize(1);
        assertWithdrawalUser(withdrawalUsers.get(0), user.getId(), user.getName(), user.getSocialId(), user.getSocialType());
    }

    private void assertWithdrawalUser(WithdrawalUser withdrawalUser, Long userId, String name, String socialId, UserSocialType socialType) {
        assertThat(withdrawalUser.getUserId()).isEqualTo(userId);
        assertThat(withdrawalUser.getName()).isEqualTo(name);
        assertThat(withdrawalUser.getSocialInfo().getSocialId()).isEqualTo(socialId);
        assertThat(withdrawalUser.getSocialInfo().getSocialType()).isEqualTo(socialType);
    }

    private void assertUserInfo(User user, String socialId, UserSocialType type, String name, UserStatusType statusType) {
        assertThat(user.getSocialInfo().getSocialId()).isEqualTo(socialId);
        assertThat(user.getSocialInfo().getSocialType()).isEqualTo(type);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getStatus()).isEqualTo(statusType);
    }

}
