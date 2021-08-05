package com.depromeet.threedollar.api.controller.user;

import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.controller.AbstractControllerTest;
import com.depromeet.threedollar.api.service.user.dto.request.CheckAvailableNameRequest;
import com.depromeet.threedollar.api.service.user.dto.request.UpdateUserInfoRequest;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.user.UserCreator;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.depromeet.threedollar.common.exception.ErrorCode.CONFLICT_EXCEPTION;
import static com.depromeet.threedollar.common.exception.ErrorCode.UNAUTHORIZED_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends AbstractControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
    }

    @DisplayName("GET /api/v2/user/me 200 OK")
    @Test
    void 자신의_회원정보를_조회하는_API_호출시_정상적으로_회원정보가_조회된다() throws Exception {
        // when
        ApiResponse<UserInfoResponse> response = userMockApiCaller.getMyUserInfo(token, 200);

        // then
        assertUserInfoResponse(response.getData(), testUser.getId(), testUser.getName(), testUser.getSocialType());
    }

    @DisplayName("GET /api/v2/user/me 잘못된 세션일 경우 401 Error")
    @Test
    void 자신의_회원정보를_조회하는_API_호출시_잘못된_세션이면_401_에러가_발생한다() throws Exception {
        // when
        ApiResponse<UserInfoResponse> response = userMockApiCaller.getMyUserInfo("wrong token", 401);

        // then
        assertThat(response.getResultCode()).isEqualTo(UNAUTHORIZED_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(UNAUTHORIZED_EXCEPTION.getMessage());
        assertThat(response.getData()).isNull();
    }

    @DisplayName("PUT /api/v2/user/me 200 OK")
    @Test
    void 자신의_회원정보를_수정하는_API_호출시_정상적으로_수정된다() throws Exception {
        // given
        String name = "디프만";

        UpdateUserInfoRequest request = UpdateUserInfoRequest.testInstance(name);

        // when
        ApiResponse<UserInfoResponse> response = userMockApiCaller.updateMyUserInfo(request, token, 200);

        // then
        assertUserInfoResponse(response.getData(), testUser.getId(), name, testUser.getSocialType());
    }

    @DisplayName("PUT /api/v2/user/me 중복된 닉네임일 경우 409 Error")
    @Test
    void 자신의_회원정보를_수정하는_API_호출시_닉네임이_중복되는경우_409_에러가_발생한다() throws Exception {
        // given
        String name = "디프만";
        userRepository.save(UserCreator.create("social-social-id", UserSocialType.APPLE, name));

        UpdateUserInfoRequest request = UpdateUserInfoRequest.testInstance(name);

        // when
        ApiResponse<UserInfoResponse> response = userMockApiCaller.updateMyUserInfo(request, token, 409);

        // then
        assertThat(response.getResultCode()).isEqualTo(CONFLICT_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(CONFLICT_EXCEPTION.getMessage());
        assertThat(response.getData()).isNull();
    }

    @DisplayName("PUT /api/v2/user/me 잘못된 세션일 경우 401 Error")
    @Test
    void 자신의_회원정보를_수정하는_API_호출시_잘못된_세션일경우_401_에러가_발생한다() throws Exception {
        // given
        UpdateUserInfoRequest request = UpdateUserInfoRequest.testInstance("디프만");

        // when
        ApiResponse<UserInfoResponse> response = userMockApiCaller.updateMyUserInfo(request, "wrong token", 401);

        // then
        assertThat(response.getResultCode()).isEqualTo(UNAUTHORIZED_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(UNAUTHORIZED_EXCEPTION.getMessage());
        assertThat(response.getData()).isNull();
    }

    @DisplayName("GET /api/v2/user/me/name/check 사용가능한 닉네임인경우 200 OK")
    @Test
    void 사용가능한_닉네임인지_확인하는_API_호출시_사용가능하면_200_OK() throws Exception {
        // given
        String name = "디프만";
        CheckAvailableNameRequest request = CheckAvailableNameRequest.testInstance(name);

        // when
        ApiResponse<String> response = userMockApiCaller.checkAvailableName(request, 200);

        // then
        assertThat(response.getData()).isEqualTo("OK");
    }

    @DisplayName("GET /api/v2/user/me/name/check 중복된 이름인경우 409 Error")
    @Test
    void 사용가능한_닉네임인지_확인하는_API_호출시_중복된_이름인경우_409_에러가_발생한다() throws Exception {
        // given
        String name = "디프만";
        userRepository.save(UserCreator.create("social-social-id", UserSocialType.APPLE, name));
        CheckAvailableNameRequest request = CheckAvailableNameRequest.testInstance(name);

        // when
        ApiResponse<String> response = userMockApiCaller.checkAvailableName(request, 409);

        // then
        assertThat(response.getResultCode()).isEqualTo(CONFLICT_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(CONFLICT_EXCEPTION.getMessage());
        assertThat(response.getData()).isNull();
    }

    private void assertUserInfoResponse(UserInfoResponse response, Long userId, String name, UserSocialType socialType) {
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getSocialType()).isEqualTo(socialType);
    }

}
