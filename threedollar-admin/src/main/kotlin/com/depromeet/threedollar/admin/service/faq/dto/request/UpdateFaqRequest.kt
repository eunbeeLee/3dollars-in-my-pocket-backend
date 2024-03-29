package com.depromeet.threedollar.admin.service.faq.dto.request

import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class UpdateFaqRequest(
    val question: String,
    val answer: String,
    val category: FaqCategory
)
