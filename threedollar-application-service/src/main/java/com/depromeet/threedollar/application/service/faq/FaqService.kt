package com.depromeet.threedollar.application.service.faq

import com.depromeet.threedollar.application.config.cache.CacheType
import com.depromeet.threedollar.application.service.faq.dto.request.RetrieveFaqsRequest
import com.depromeet.threedollar.application.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FaqService(
    private val faqRepository: FaqRepository
) {

    @Cacheable(key = "#request.category?:'ALL'", value = [CacheType.CacheKey.FAQS])
    @Transactional(readOnly = true)
    fun retrieveAllFaqs(request: RetrieveFaqsRequest): List<FaqResponse> {
        return faqRepository.findAllByCategory(request.category).asSequence()
            .sortedBy { it.category.displayOrder }
            .map { FaqResponse.of(it) }
            .toList()
    }

}
