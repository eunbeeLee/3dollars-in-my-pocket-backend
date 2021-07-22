package com.depromeet.threedollar.admin.service.admin.dto.response

import com.depromeet.threedollar.admin.common.dto.AuditingTimeResponse
import com.depromeet.threedollar.domain.domain.admin.Admin

data class AdminInfoResponse(
    val email: String,
    val name: String,
) : AuditingTimeResponse() {

    companion object {
        fun of(admin: Admin): AdminInfoResponse {
            val response = AdminInfoResponse(admin.email, admin.name)
            response.setAuditingTime(admin)
            return response
        }
    }

}
