package com.depromeet.threedollar.api.controller;

import com.depromeet.threedollar.api.service.jwt.JwtService;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"local", "local-will"})
@RequiredArgsConstructor
@RestController
public class TestController {

	private static final User testUser = User.newInstance("test-uid", UserSocialType.KAKAO, "관리자 계정");

	private final JwtService jwtService;
	private final UserRepository userRepository;

	@GetMapping("/test-token")
	public ApiResponse<String> getTestToken() {
		User user = userRepository.findUserBySocialIdAndSocialType(testUser.getSocialId(), testUser.getSocialType());
		if (user == null) {
			user = userRepository.save(testUser);
		}
		String token = jwtService.encode(user.getId());
		return ApiResponse.success(token);
	}

}
