package com.depromeet.threedollar.api.service.jwt;

import com.depromeet.threedollar.api.service.token.TokenService;
import com.depromeet.threedollar.api.service.token.dto.UserTokenDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private TokenService jwtService;

    @Test
    void userId를통해_토큰을_생성한다() {
        // given
        long userId = 100L;

        // when
        String token = jwtService.encode(new UserTokenDto(userId));

        // then
        assertThat(token.startsWith("ey")).isTrue();
    }

    @Test
    void 토큰을_복호화하면_userID가_반환된다() {
        // given
        Long userId = 10L;
        String token = jwtService.encode(new UserTokenDto(userId));

        // when
        Long result = jwtService.decode(token).getUserId();

        // then
        assertThat(result).isEqualTo(userId);
    }

}
