package com.depromeet.threedollar.api.service.faq.dto.response

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse
import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class FaqResponse(
    val faqId: Long,
    val question: String,
    val answer: String,
    val category: FaqCategory
) : AuditingTimeResponse() {

    companion object {
        fun of(faq: Faq): FaqResponse {
            val response = FaqResponse(faq.id, faq.question, faq.answer, faq.category)
            response.setBaseTime(faq)
            return response
        }
    }

}
