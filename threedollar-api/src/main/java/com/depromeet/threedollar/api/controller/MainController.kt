package com.depromeet.threedollar.api.controller

import com.depromeet.threedollar.api.common.dto.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/ping")
    fun healthCheck(): ApiResponse<String> {
        return ApiResponse.SUCCESS
    }

    @GetMapping("/version")
    fun checkVersion(
        @RequestHeader("User-Agent") userAgent: String
    ): ApiResponse<String> {
        // TODO User-Agent의 버전에 따라서 505 에러를 발생.
        return ApiResponse.SUCCESS
    }

}
