package com.depromeet.threedollar.admin.service.token.dto.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
data class JwtTokenProviderComponent(
    val secretKey: String,
    val issuer: String
)
