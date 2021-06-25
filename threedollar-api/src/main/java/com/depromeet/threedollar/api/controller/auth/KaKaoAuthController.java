package com.depromeet.threedollar.api.controller.auth;

import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.auth.AuthService;
import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.threedollar.api.service.auth.dto.response.LoginResponse;
import com.depromeet.threedollar.api.service.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class KaKaoAuthController {

	private final AuthService kaKaoAuthService;
	private final JwtService jwtService;

	@Operation(summary = "카카오 계정의 회원가입을 요청하는 API")
	@PostMapping("/api/v2/signup/kakao")
	public ApiResponse<LoginResponse> signUpKaKao(@Valid @RequestBody SignUpRequest request) {
		Long userId = kaKaoAuthService.signUp(request);
		return ApiResponse.success(LoginResponse.of(jwtService.encodeSignUpToken(userId)));
	}

	@Operation(summary = "카카오 계정의 로그인을 요청하는 API")
	@PostMapping("/api/v2/login/kakao")
	public ApiResponse<LoginResponse> loginKaKao(@Valid @RequestBody LoginRequest request) {
		Long userId = kaKaoAuthService.login(request);
		return ApiResponse.success(LoginResponse.of(jwtService.encodeSignUpToken(userId)));
	}

	@Operation(summary = "카카오 계정의 회원탈퇴 요청하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@DeleteMapping("/api/v2/signout/kakao")
	public ApiResponse<String> signOutKakao() {
		return ApiResponse.SUCCESS;
	}

}
