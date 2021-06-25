package com.depromeet.threedollar.api.service.auth;

import com.depromeet.threedollar.api.service.auth.dto.request.LoginRequest;
import com.depromeet.threedollar.api.service.auth.dto.request.SignUpRequest;

public interface AuthService {

	void signUp(SignUpRequest request);

	void login(LoginRequest request);

	void signOut();

}
