package com.depromeet.threedollar.admin.service.store.dto.response

import com.depromeet.threedollar.admin.common.dto.AuditingTimeResponse
import com.depromeet.threedollar.domain.domain.store.StoreType
import com.depromeet.threedollar.domain.domain.storedelete.repository.projection.ReportedStoreProjection

data class ReportedStoresResponse(
    val storeId: Long,
    val storeName: String,
    val latitude: Double,
    val longitude: Double,
    val type: StoreType,
    val rating: Double,
    val reportsCount: Long,
) : AuditingTimeResponse() {

    companion object {
        fun of(projection: ReportedStoreProjection): ReportedStoresResponse {
            val response = ReportedStoresResponse(
                projection.storeId,
                projection.storeName,
                projection.latitude,
                projection.longitude,
                projection.type,
                projection.rating,
                projection.reportsCount
            )
            response.setAuditingTime(projection.storeCreatedAt, projection.storeUpdatedAt)
            return response
        }
    }

}
