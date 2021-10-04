package com.depromeet.threedollar.admin.service.store.dto.request

import javax.validation.constraints.Min

data class RetrieveReportedStoresRequest(
    val minCount: Int,

    @field:Min(1, message = "{common.size.page}")
    val page: Long,

    @field:Min(1, message = "{common.size.min}")
    val size: Int,
)
