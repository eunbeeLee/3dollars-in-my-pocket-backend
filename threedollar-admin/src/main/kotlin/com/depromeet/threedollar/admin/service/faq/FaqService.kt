package com.depromeet.threedollar.admin.service.faq

import com.depromeet.threedollar.admin.service.faq.dto.request.AddFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.RetrieveFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.UpdateFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
import com.depromeet.threedollar.domain.domain.faq.FaqRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FaqService(
    private val faqRepository: FaqRepository
) {

    @Transactional
    fun addFaq(request: AddFaqRequest): FaqResponse {
        return FaqResponse.of(faqRepository.save(request.toEntity()))
    }

    @Transactional
    fun updateFaq(faqId: Long, request: UpdateFaqRequest): FaqResponse {
        val faq = FaqServiceUtils.findFaqById(faqRepository, faqId)
        faq.update(request.question, request.answer, request.category)
        return FaqResponse.of(faq)
    }

    @Transactional
    fun deleteFaq(faqId: Long) {
        val faq = FaqServiceUtils.findFaqById(faqRepository, faqId)
        faqRepository.delete(faq)
    }

    @Transactional(readOnly = true)
    fun retrieveFaqs(request: RetrieveFaqRequest): List<FaqResponse> {
        return faqRepository.findAllByCategory(request.category).asSequence()
            .sortedBy { it.category.displayOrder }
            .map { FaqResponse.of(it) }
            .toList()
    }

}
