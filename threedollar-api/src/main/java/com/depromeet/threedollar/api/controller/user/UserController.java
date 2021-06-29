package com.depromeet.threedollar.api.controller.user;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.user.UserService;
import com.depromeet.threedollar.api.service.user.dto.request.UpdateUserInfoRequest;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@Operation(summary = "내 정보를 조회합니다. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@GetMapping("/api/v2/user/me")
	public ApiResponse<UserInfoResponse> getMyUserInfo(@UserId Long userId) {
		return ApiResponse.success(userService.getUserInfo(userId));
	}

	@Operation(summary = "내 정보를 수정합니다. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PutMapping("/api/v2/user/me")
	public ApiResponse<UserInfoResponse> updateMyUserInfo(@Valid @RequestBody UpdateUserInfoRequest request, @UserId Long userId) {
		return ApiResponse.success(userService.updateUserInfo(request, userId));
	}

}
