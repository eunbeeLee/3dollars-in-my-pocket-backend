package com.depromeet.threedollar.admin.service.faq

import com.depromeet.threedollar.admin.service.faq.dto.request.AddFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.UpdateFaqRequest
import com.depromeet.threedollar.application.config.cache.CacheType
import com.depromeet.threedollar.application.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FaqAdminService(
    private val faqRepository: FaqRepository
) {

    @Caching(
        evict = [
            CacheEvict(key = "#request.category", value = [CacheType.CacheKey.FAQS]),
            CacheEvict(key = "'ALL'", value = [CacheType.CacheKey.FAQS])
        ]
    )
    @Transactional
    fun addFaq(request: AddFaqRequest): FaqResponse {
        return FaqResponse.of(faqRepository.save(request.toEntity()))
    }

    @Caching(
        evict = [
            CacheEvict(key = "#request.category", value = [CacheType.CacheKey.FAQS]),
            CacheEvict(key = "'ALL'", value = [CacheType.CacheKey.FAQS])
        ]
    )
    @Transactional
    fun updateFaq(faqId: Long, request: UpdateFaqRequest): FaqResponse {
        val faq = FaqAdminServiceUtils.findFaqById(faqRepository, faqId)
        faq.update(request.question, request.answer, request.category)
        return FaqResponse.of(faq)
    }

    @Caching(
        evict = [
            // TODO 파라미터로 카테고리가 넘어오지 않는 경우 대응
            CacheEvict(key = "'ALL'", value = [CacheType.CacheKey.FAQS])
        ]
    )
    @Transactional
    fun deleteFaq(faqId: Long) {
        val faq = FaqAdminServiceUtils.findFaqById(faqRepository, faqId)
        faqRepository.delete(faq)
    }

}
