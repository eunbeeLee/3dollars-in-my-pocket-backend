package com.depromeet.threedollar.api.service.user;

import com.depromeet.threedollar.api.service.user.dto.request.CheckDuplicateNameRequest;
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

import java.util.Arrays;
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
    void 회원가입이_성공하면_유저정보가_저장된다() {
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
        assertUserInfo(users.get(0), socialId, type, name);
    }

    @Test
    void 회원가입시_중복된_닉네임이면_에러_발생() {
        // given
        String name = "가슴속 삼천원";
        userRepository.save(UserCreator.create("social-id", UserSocialType.KAKAO, name));

        CreateUserRequest request = CreateUserRequest.testInstance("another-id", UserSocialType.APPLE, name);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원가입시_중복된_SOCIALID이면_에러_발생() {
        // given
        String socialId = "social-id";
        UserSocialType type = UserSocialType.KAKAO;
        userRepository.save(UserCreator.create(socialId, type, "기존의 닉네임"));

        CreateUserRequest request = CreateUserRequest.testInstance(socialId, type, "새로운 닉네임");

        // when & then
        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원가입시_SOCIALID가_같더라도_제공하는_소셜이_다른경우_회원가입이_정상적으로_처리된다() {
        // given
        String socialId = "social-id";
        userRepository.save(UserCreator.create(socialId, UserSocialType.APPLE, "기존의 닉네임"));

        CreateUserRequest request = CreateUserRequest.testInstance(socialId, UserSocialType.KAKAO, "새로운 닉네임");

        // when
        userService.createUser(request);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertUserInfo(users.get(0), socialId, UserSocialType.APPLE, "기존의 닉네임");
        assertUserInfo(users.get(1), socialId, UserSocialType.KAKAO, "새로운 닉네임");
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
    void 유저_정보를_조회시_존재하지_않는_유저면_에러가_발생한다() {
        // given
        Long userId = 999L;

        // when & then
        assertThatThrownBy(() -> userService.getUserInfo(userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 중복된_닉네임인지_체크시_중복된_닉네임이면_에러가_발생한다() {
        // given
        String name = "가슴속 삼천원";
        User user = UserCreator.create("social-id", UserSocialType.KAKAO, name);
        userRepository.save(user);

        CheckDuplicateNameRequest request = CheckDuplicateNameRequest.testInstance(name);

        // when & then
        assertThatThrownBy(() -> userService.checkDuplicateName(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 중복된_닉네임인지_체크시_중복되지_않으면_OK() {
        // given
        String name = "가슴속 삼천원";

        CheckDuplicateNameRequest request = CheckDuplicateNameRequest.testInstance(name);

        // when
        userService.checkDuplicateName(request);
    }

    @Test
    void 회원정보_수정이_상공하면_DB_정보가_변경된다() {
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
        assertUserInfo(users.get(0), socialId, type, name);
    }

    @Test
    void 회원정보_수정시_존재하지_않는_유저면_에러가_발생한다() {
        // given
        Long userId = 999L;
        UpdateUserInfoRequest request = UpdateUserInfoRequest.testInstance("name");

        // when & then
        assertThatThrownBy(() -> userService.updateUserInfo(request, userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 회원탈퇴하면_백업을_위한_데이터가_생성된다() {
        // given
        UserSocialType type = UserSocialType.APPLE;
        User user = UserCreator.create("social-id", type, "기존의 닉네임");
        userRepository.save(user);

        // then
        userService.signOut(user.getId(), type);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).isEmpty();

        List<WithdrawalUser> withdrawalUsers = withdrawalUserRepository.findAll();
        assertThat(withdrawalUsers).hasSize(1);
        assertWithdrawalUser(withdrawalUsers.get(0), user.getId(), user.getName(), user.getSocialId(), user.getSocialType());
    }

    @Test
    void 회원탈퇴한_유저만이_USER_테이블에서_삭제된다() {
        // given
        UserSocialType type = UserSocialType.APPLE;
        User user1 = UserCreator.create("social-id1", type, "기존의 닉네임1");
        User user2 = UserCreator.create("social-id2", UserSocialType.APPLE, "기존의 닉네임2");

        userRepository.saveAll(Arrays.asList(user1, user2));

        // then
        userService.signOut(user1.getId(), type);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertUserInfo(users.get(0), user2.getSocialId(), user2.getSocialType(), user2.getName());
    }

    private void assertWithdrawalUser(WithdrawalUser withdrawalUser, Long userId, String name, String socialId, UserSocialType socialType) {
        assertThat(withdrawalUser.getUserId()).isEqualTo(userId);
        assertThat(withdrawalUser.getName()).isEqualTo(name);
        assertThat(withdrawalUser.getSocialInfo().getSocialId()).isEqualTo(socialId);
        assertThat(withdrawalUser.getSocialInfo().getSocialType()).isEqualTo(socialType);
    }

    private void assertUserInfo(User user, String socialId, UserSocialType type, String name) {
        assertThat(user.getSocialInfo().getSocialId()).isEqualTo(socialId);
        assertThat(user.getSocialInfo().getSocialType()).isEqualTo(type);
        assertThat(user.getName()).isEqualTo(name);
    }

}
