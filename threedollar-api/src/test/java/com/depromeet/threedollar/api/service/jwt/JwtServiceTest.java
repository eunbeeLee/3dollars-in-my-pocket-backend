package com.depromeet.threedollar.api.service.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtServiceTest {

	@Autowired
	private JwtService jwtService;

	@Test
	void userId를통해_토큰을_생성한다() {
		// given
		Long userId = 100L;

		// when
		String token = jwtService.encodeSignUpToken(userId);

		// then
		assertThat(token.startsWith("ey")).isTrue();
	}

	@Test
	void 토큰을_복호화하면_userID가_반환된다() {
		// given
		Long userId = 10L;
		String token = jwtService.encodeSignUpToken(userId);

		// when
		Long result = jwtService.decodeSignUpToken(token);

		// then
		assertThat(result).isEqualTo(userId);
	}

}