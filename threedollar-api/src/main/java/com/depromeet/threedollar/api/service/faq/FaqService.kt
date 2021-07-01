package com.depromeet.threedollar.api.service.faq

import com.depromeet.threedollar.api.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FaqService(
    private val faqRepository: FaqRepository
) {

    @Transactional(readOnly = true)
    fun retrieveAllFaqs(): List<FaqResponse> {
        return faqRepository.findAll()
            .map { FaqResponse.of(it) }
    }

}