package com.depromeet.threedollar.admin.service.store.dto.request

import javax.validation.constraints.Min

data class RetrieveLatestStoresRequest(
    @field:Min(1, message = "{common.size.min}")
    val size: Int,
    val cursor: Long?
)
