package com.depromeet.threedollar.admin.service.faq.dto.request

import com.depromeet.threedollar.domain.domain.faq.FaqCategory

data class RetrieveFaqRequest(
    val category: FaqCategory?
)
