package com.depromeet.threedollar.api.controller.auth;

import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.threedollar.api.service.auth.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class KaKaoAuthController {

	@Operation(summary = "카카오 계정의 회원가입을 요청하는 API")
	@PostMapping("/api/v2/signup/kakao")
	public ApiResponse<LoginResponse> signUpKaKao(@Valid @RequestBody SignUpRequest request) {
		return ApiResponse.success(null);
	}

	@Operation(summary = "카카오 계정의 로그인을 요청하는 API")
	@PostMapping("/api/v2/login/kakao")
	public ApiResponse<LoginResponse> loginKaKao(@Valid @RequestBody LoginRequest request) {
		return ApiResponse.success(null);
	}

	@Operation(summary = "카카오 계정의 회원탈퇴 요청하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@DeleteMapping("/api/v2/signout/kakao")
	public ApiResponse<String> signOutKakao() {
		return ApiResponse.SUCCESS;
	}

}
