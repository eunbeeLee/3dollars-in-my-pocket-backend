package com.depromeet.threedollar.api.config.interceptor;

import com.depromeet.threedollar.api.service.jwt.JwtService;
import com.depromeet.threedollar.api.service.user.UserServiceUtils;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginCheckHandler {

	private final UserRepository userRepository;

	private final JwtService jwtService;

	public Long getUserId(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long userId = jwtService.decodeSignUpToken(token);
		UserServiceUtils.validateExistsUser(userRepository, userId);
		return userId;
	}

}
