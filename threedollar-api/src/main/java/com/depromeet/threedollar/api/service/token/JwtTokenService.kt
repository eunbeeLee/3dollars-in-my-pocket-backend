package com.depromeet.threedollar.api.service.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.depromeet.threedollar.api.service.token.dto.UserTokenDto
import com.depromeet.threedollar.api.service.token.dto.component.JwtTokenProviderComponent
import com.depromeet.threedollar.common.exception.UnAuthorizedException
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtTokenService(
    private val jwtTokenProviderComponent: JwtTokenProviderComponent
) : TokenService {

    companion object {
        private const val EXPIRES_TIME: Long = 60 * 60 * 24 * 15
    }

    override fun encode(userTokenDto: UserTokenDto): String {
        try {
            val now = ZonedDateTime.now()
            return JWT.create()
                .withHeader(creatJwtHeader())
                .withIssuer(jwtTokenProviderComponent.issuer)
                .withClaim("userId", userTokenDto.userId.toInt())
                .withIssuedAt(Date.from(now.toInstant()))
                .withExpiresAt(Date.from(now.toInstant().plusSeconds(EXPIRES_TIME)))
                .sign(Algorithm.HMAC512(jwtTokenProviderComponent.secretKey))
        } catch (e: JWTCreationException) {
            throw IllegalArgumentException("토큰 생성이 실패하였습니다 ($userTokenDto)")
        }
    }

    private fun creatJwtHeader(): Map<String, Any> {
        val headers: MutableMap<String, Any> = HashMap()
        headers["typ"] = "JWT"
        return headers
    }

    override fun decode(token: String): UserTokenDto {
        try {
            val jwt = createJwtVerifier().verify(token)
            val userId = jwt.claims["userId"] ?: throw UnAuthorizedException("잘못된 토큰입니다. 다시 로그인해주세요.")
            return UserTokenDto(userId.asInt().toLong())
        } catch (e: RuntimeException) {
            throw UnAuthorizedException("토큰 Decode 에 실패하였습니다 ($token)")
        }
    }

    private fun createJwtVerifier(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(jwtTokenProviderComponent.secretKey))
            .withIssuer(jwtTokenProviderComponent.issuer)
            .build()
    }

}
