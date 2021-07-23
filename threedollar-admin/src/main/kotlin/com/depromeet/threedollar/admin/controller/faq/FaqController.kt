package com.depromeet.threedollar.admin.controller.faq

import com.depromeet.threedollar.admin.common.dto.ApiResponse
import com.depromeet.threedollar.admin.service.faq.FaqService
import com.depromeet.threedollar.admin.service.faq.dto.request.AddFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.RetrieveFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.UpdateFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.response.FaqCategoryResponse
import com.depromeet.threedollar.admin.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
class FaqController(
    private val faqService: FaqService
) {

    @ApiOperation("새로운 FAQ를 등록합니다.")
    @PostMapping("/admin/v1/faq")
    fun addFaq(
        @RequestBody request: AddFaqRequest
    ): ApiResponse<FaqResponse> {
        return ApiResponse.success(faqService.addFaq(request))
    }

    @ApiOperation("등록된 FAQ를 수정합니다.")
    @PutMapping("/admin/v1/faq/{faqId}")
    fun updateFaq(
        @PathVariable faqId: Long,
        @RequestBody request: UpdateFaqRequest
    ): ApiResponse<FaqResponse> {
        return ApiResponse.success(faqService.updateFaq(faqId, request))
    }

    @ApiOperation("등록된 FAQ를 삭제합니다.")
    @DeleteMapping("/admin/v1/faq/{faqId}")
    fun deleteFaq(
        @PathVariable faqId: Long
    ): ApiResponse<String> {
        faqService.deleteFaq(faqId)
        return ApiResponse.OK
    }

    @ApiOperation("등록된 FAQ들을 조회한다")
    @GetMapping("/admin/v1/faqs")
    fun retrieveFaqs(
        request: RetrieveFaqRequest
    ): ApiResponse<List<FaqResponse>> {
        return ApiResponse.success(faqService.retrieveFaqs(request))
    }

    @ApiOperation("등록된 FAQ 카테고리를 조회한다")
    @GetMapping("/admin/v1/faq/categories")
    fun retrieveFaqCategories(): ApiResponse<List<FaqCategoryResponse>> {
        val response = FaqCategory.values().asSequence()
            .sortedBy { it.displayOrder }
            .map { FaqCategoryResponse.of(it) }
            .toList()
        return ApiResponse.success(response)
    }

}
