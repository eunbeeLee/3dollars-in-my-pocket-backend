package com.depromeet.threedollar.api.service.faq.dto.response

import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class FaqCategoryResponse(
    val category: FaqCategory,
    val description: String
) {

    companion object {
        fun of(category: FaqCategory): FaqCategoryResponse {
            return FaqCategoryResponse(category, category.description)
        }
    }

}
