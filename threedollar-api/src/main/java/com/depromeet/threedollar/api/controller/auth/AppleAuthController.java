package com.depromeet.threedollar.api.controller.auth;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
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
public class AppleAuthController {

    private final AuthService appleAuthService;
    private final JwtService jwtService;

    @Operation(summary = "Apple 계정의 회원가입을 요청하는 API")
    @PostMapping("/api/v2/signup/apple")
    public ApiResponse<LoginResponse> signUpApple(@Valid @RequestBody SignUpRequest request) {
        Long userId = appleAuthService.signUp(request);
        return ApiResponse.success(LoginResponse.of(jwtService.encodeSignUpToken(userId)));
    }

    @Operation(summary = "Apple 계정의 로그인을 요청하는 API")
    @PostMapping("/api/v2/login/apple")
    public ApiResponse<LoginResponse> loginApple(@Valid @RequestBody LoginRequest request) {
        Long userId = appleAuthService.login(request);
        return ApiResponse.success(LoginResponse.of(jwtService.encodeSignUpToken(userId)));
    }

    @Operation(summary = "Apple 계정의 회원탈퇴 요청하는 API. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @DeleteMapping("/api/v2/signout/apple")
    public ApiResponse<String> signOutApple(@UserId Long userId) {
        appleAuthService.signOut(userId);
        return ApiResponse.SUCCESS;
    }

}
