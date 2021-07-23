package com.depromeet.threedollar.admin.service.faq.dto.response

import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class FaqCategoryResponse(
    val category: FaqCategory,
    val description: String,
    val displayOrder: Int
) {

    companion object {
        fun of(category: FaqCategory): FaqCategoryResponse {
            return FaqCategoryResponse(category, category.description, category.displayOrder)
        }
    }

}
