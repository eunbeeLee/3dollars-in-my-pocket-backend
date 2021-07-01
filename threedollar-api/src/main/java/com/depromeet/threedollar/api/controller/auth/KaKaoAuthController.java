package com.depromeet.threedollar.api.controller.auth;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.auth.AuthService;
import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.threedollar.api.service.auth.dto.response.LoginResponse;
import com.depromeet.threedollar.api.service.jwt.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("카카오 계정의 회원가입을 요청하는 API")
    @PostMapping("/api/v2/signup/kakao")
    public ApiResponse<LoginResponse> signUpKaKao(@Valid @RequestBody SignUpRequest request) {
        Long userId = kaKaoAuthService.signUp(request);
        return ApiResponse.success(LoginResponse.of(jwtService.encode(userId)));
    }

    @ApiOperation("카카오 계정의 로그인을 요청하는 API")
    @PostMapping("/api/v2/login/kakao")
    public ApiResponse<LoginResponse> loginKaKao(@Valid @RequestBody LoginRequest request) {
        Long userId = kaKaoAuthService.login(request);
        return ApiResponse.success(LoginResponse.of(jwtService.encode(userId)));
    }

    @ApiOperation("Apple 계정의 회원탈퇴 요청하는 API. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("/api/v2/signout/kakao")
    public ApiResponse<String> signOutKakao(@UserId Long userId) {
        kaKaoAuthService.signOut(userId);
        return ApiResponse.SUCCESS;
    }

}
