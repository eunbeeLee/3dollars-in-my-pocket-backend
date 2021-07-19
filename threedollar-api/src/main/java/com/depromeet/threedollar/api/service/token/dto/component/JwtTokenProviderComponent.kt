package com.depromeet.threedollar.api.service.token.dto.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "jwt")
@Configuration
class JwtTokenProviderComponent {

    lateinit var secretKey: String

    lateinit var issuer: String

}
