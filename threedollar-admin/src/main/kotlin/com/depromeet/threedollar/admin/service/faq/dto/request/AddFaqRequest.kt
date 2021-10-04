package com.depromeet.threedollar.admin.service.faq.dto.request

import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class AddFaqRequest(
    val category: FaqCategory,
    val question: String,
    val answer: String
) {

    fun toEntity(): Faq {
        return Faq.newInstance(category, question, answer)
    }

}
