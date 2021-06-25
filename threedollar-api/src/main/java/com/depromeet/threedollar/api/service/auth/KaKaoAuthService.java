package com.depromeet.threedollar.api.service.auth;

import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KaKaoAuthService implements AuthService {

	@Override
	public void signUp(SignUpRequest request) {

	}

	@Override
	public void login(LoginRequest request) {

	}

	@Override
	public void signOut() {

	}

}
