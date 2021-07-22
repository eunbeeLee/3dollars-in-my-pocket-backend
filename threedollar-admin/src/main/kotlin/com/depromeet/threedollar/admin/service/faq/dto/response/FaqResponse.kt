package com.depromeet.threedollar.admin.service.faq.dto.response

import com.depromeet.threedollar.admin.common.dto.AuditingTimeResponse
import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class FaqResponse(
    val faqId: Long,
    val category: FaqCategory,
    val question: String,
    val answer: String
) : AuditingTimeResponse() {

    companion object {
        fun of(faq: Faq): FaqResponse {
            val response = FaqResponse(faq.id, faq.category, faq.question, faq.answer)
            response.setAuditingTime(faq)
            return response
        }
    }

}
