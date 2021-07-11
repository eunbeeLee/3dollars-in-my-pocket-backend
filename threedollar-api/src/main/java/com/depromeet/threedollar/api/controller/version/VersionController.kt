package com.depromeet.threedollar.api.controller.version

import com.depromeet.threedollar.api.common.dto.ApiResponse
import com.depromeet.threedollar.api.service.version.VersionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class VersionController(
    private val versionService: VersionService
) {

    /**
     * 강제 업데이트를 위한 버전 체크 API
     * 기존 백엔드 서버의 호환성을 위해서 위 API를 사용. (기존 백엔드 서버에 신규 배포가 거의 불가능한 상황)
     */
    @GetMapping("/actuator/health")
    fun checkVersion(
        @RequestHeader("User-Agent") userAgent: String
    ): ApiResponse<String> {
        versionService.checkAvailableVersion(userAgent)
        return ApiResponse.SUCCESS
    }

}
