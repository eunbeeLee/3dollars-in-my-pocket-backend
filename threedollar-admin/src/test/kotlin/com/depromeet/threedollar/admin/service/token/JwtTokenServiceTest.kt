package com.depromeet.threedollar.admin.service.token

import com.depromeet.threedollar.admin.service.token.dto.AdminTokenDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class JwtTokenServiceTest(
    @Autowired
    private val jwtTokenService: JwtTokenService
) {

    @Test
    fun 관리자_PK로_새로운_토큰을_생성한다() {
        // given
        val tokenDto = AdminTokenDto(1L)

        // when
        val token = jwtTokenService.encode(tokenDto)

        // then
        assertThat(token).isNotEqualTo(1)
        assertThat(token.startsWith("ey"))
    }

    @Test
    fun 생성된_토큰을_디코딩하면_원본의_관리자_PK가_디코딩된다() {
        // given
        val tokenDto = AdminTokenDto(1L)
        val token = jwtTokenService.encode(tokenDto)

        // when
        val result = jwtTokenService.decode(token)

        // then
        assertThat(result).isEqualTo(tokenDto)
    }

}
