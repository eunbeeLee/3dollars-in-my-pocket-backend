package com.depromeet.threedollar.admin.controller.faq

import com.depromeet.threedollar.admin.common.dto.ApiResponse
import com.depromeet.threedollar.admin.service.faq.FaqService
import com.depromeet.threedollar.admin.service.faq.dto.request.AddFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.response.FaqResponse
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FaqController(
    private val faqService: FaqService
) {

    @ApiOperation("새로운 FAQ를 등록합니다.")
    @PostMapping("/admin/v1/faq")
    fun addFaq(@RequestBody request: AddFaqRequest): ApiResponse<FaqResponse> {
        return ApiResponse.success(faqService.addFaq(request))
    }

}
