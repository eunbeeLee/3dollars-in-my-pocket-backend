package com.depromeet.threedollar.api.service.faq.dto.response

import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
import lombok.ToString

@ToString
data class FaqResponse(
    val id: Long?,
    val question: String,
    val answer: String,
    val category: FaqCategory
) {

    companion object {
        fun of(faq: Faq): FaqResponse {
            return FaqResponse(faq.id, faq.question, faq.answer, faq.category)
        }
    }

}
