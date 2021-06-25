package com.depromeet.threedollar.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

	/**
	 * 회원가입 API
	 */
	@PostMapping("/api/v1/signup")
	public void signUp() {

	}

	/**
	 * 내 정보를 가져오는 API
	 */
	@GetMapping("/api/v1/user/me")
	public void getMyUserInfo() {

	}

	/**
	 * 내 정보를 수정하는 API
	 */
	@PutMapping("/api/v1/user/me")
	public void updateMyUserInfo() {

	}

}
