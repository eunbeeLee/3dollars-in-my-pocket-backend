package com.depromeet.threedollar.api.service.version.dto.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("agent")
class VersionComponent {

    lateinit var iosPrefix: String

    lateinit var iosMajorVersion: String

}
