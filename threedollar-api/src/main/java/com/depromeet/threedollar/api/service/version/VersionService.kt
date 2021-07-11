package com.depromeet.threedollar.api.service.version

import com.depromeet.threedollar.api.service.version.dto.component.VersionComponent
import com.depromeet.threedollar.common.exception.HttpVersionNotSupportedException
import org.springframework.stereotype.Component
import java.lang.NumberFormatException

@Component
class VersionService(
    private val versionComponent: VersionComponent
) {

    fun checkAvailableVersion(userAgent: String) {
        if (isIOSAgent(userAgent)) {
            checkAvailableIosVersion(userAgent)
        }
    }

    private fun checkAvailableIosVersion(userAgent: String) {
        val versions = userAgent.split(versionComponent.iosPrefix)[0].trim().split(".")
        try {
            val majorVersion = versions[0].toInt()
            if (majorVersion < versionComponent.iosMajorVersion.toInt()) {
                throw HttpVersionNotSupportedException("허용하지 않는 IOS 버전입니다. 현재 IOS 기준, 호환가능한 최소 버전은 (${versionComponent.iosMajorVersion}) 입니다")
            }
        } catch (e: NumberFormatException) {
            throw HttpVersionNotSupportedException("허용하지 않는 IOS 버전입니다. 현재 IOS 기준, 호환가능한 최소 버전은 (${versionComponent.iosMajorVersion}) 입니다")
        }
    }

    private fun isIOSAgent(userAgent: String): Boolean {
        return userAgent.indexOf(versionComponent.iosPrefix) != -1
    }

}
