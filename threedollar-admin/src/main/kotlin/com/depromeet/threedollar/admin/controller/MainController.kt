package com.depromeet.threedollar.admin.controller

import com.depromeet.threedollar.admin.common.dto.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/ping")
    fun healthCheck(): ApiResponse<String> {
        return ApiResponse.OK
    }

}
