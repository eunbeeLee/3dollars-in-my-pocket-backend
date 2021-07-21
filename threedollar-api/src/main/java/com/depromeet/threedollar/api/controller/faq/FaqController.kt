package com.depromeet.threedollar.api.controller.faq

import com.depromeet.threedollar.api.common.dto.ApiResponse
import com.depromeet.threedollar.api.service.faq.FaqService
import com.depromeet.threedollar.api.service.faq.dto.request.RetrieveFaqRequest
import com.depromeet.threedollar.api.service.faq.dto.response.FaqCategoryResponse
import com.depromeet.threedollar.api.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FaqController(
    private val faqService: FaqService
) {

    @ApiOperation("모든 FAQ를 조회합니다")
    @GetMapping("/api/v2/faqs")
    fun retrieveAllFaqs(
        request: RetrieveFaqRequest
    ): ApiResponse<List<FaqResponse>> {
        return ApiResponse.success(faqService.retrieveAllFaqs(request))
    }

    @ApiOperation("모든 FAQ 카테고리를 조회합니다")
    @GetMapping("/api/v2/faq/categories")
    fun retrieveAllFaqCategories(): ApiResponse<List<FaqCategoryResponse>> {
        val response = FaqCategory.values().asSequence()
            .sortedBy { it.displayOrder }
            .map { FaqCategoryResponse.of(it) }
            .toList()
        return ApiResponse.success(response)
    }

}
