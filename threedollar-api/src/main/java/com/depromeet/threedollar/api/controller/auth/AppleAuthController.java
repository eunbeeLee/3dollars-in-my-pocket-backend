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
public class AppleAuthController {

	@Operation(summary = "Apple 계정의 회원가입을 요청하는 API")
	@PostMapping("/api/v2/signup/apple")
	public ApiResponse<LoginResponse> signUpApple(@Valid @RequestBody SignUpRequest request) {
		return ApiResponse.success(null);
	}

	@Operation(summary = "Apple 계정의 로그인을 요청하는 API")
	@PostMapping("/api/v2/login/apple")
	public ApiResponse<LoginResponse> loginApple(@Valid @RequestBody LoginRequest request) {
		return ApiResponse.success(null);
	}

	@Operation(summary = "Apple 계정의 회원탈퇴 요청하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@DeleteMapping("/api/v2/signout/apple")
	public ApiResponse<String> signOutApple() {
		return ApiResponse.SUCCESS;
	}

}
