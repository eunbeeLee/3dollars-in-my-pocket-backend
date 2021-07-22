package com.depromeet.threedollar.admin.service.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.depromeet.threedollar.admin.service.token.dto.AdminTokenDto
import com.depromeet.threedollar.admin.service.token.dto.component.JwtTokenProviderComponent
import com.depromeet.threedollar.common.exception.UnAuthorizedException
import com.depromeet.threedollar.common.exception.ValidationException
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtTokenService(
    private val component: JwtTokenProviderComponent
) : TokenService {

    companion object {
        private const val EXPIRES_TIME: Long = 60 * 60 * 24 // 1일
    }

    override fun encode(adminTokenDto: AdminTokenDto): String {
        return try {
            val now = ZonedDateTime.now()
            JWT.create()
                .withIssuer(component.issuer)
                .withClaim("adminId", adminTokenDto.adminId)
                .withIssuedAt(Date.from(now.toInstant()))
                .withExpiresAt(Date.from(now.toInstant().plusSeconds(EXPIRES_TIME)))
                .sign(Algorithm.HMAC512(component.secretKey))
        } catch (e: RuntimeException) {
            throw ValidationException("토큰 생성이 실패하였습니다. 다시 시도해주세요.)")
        }
    }

    override fun decode(token: String): AdminTokenDto {
        try {
            val jwt = JWT.require(Algorithm.HMAC512(component.secretKey))
                .withIssuer(component.issuer)
                .build()
                .verify(token)
            val claims = jwt.claims["adminId"] ?: throw UnAuthorizedException("잘못된 토큰입니다. 다시 로그인해주세요.")
            return AdminTokenDto(claims.asInt().toLong())
        } catch (e: RuntimeException) {
            throw UnAuthorizedException("잘못된 토큰입니다. 다시 로그인 해주세요")
        }
    }

}
