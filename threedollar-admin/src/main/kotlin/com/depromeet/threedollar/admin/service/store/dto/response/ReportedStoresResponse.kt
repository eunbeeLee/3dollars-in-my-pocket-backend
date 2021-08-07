package com.depromeet.threedollar.admin.service.store.dto.response

import com.depromeet.threedollar.application.common.dto.AuditingTimeResponse
import com.depromeet.threedollar.domain.domain.store.StoreType
import com.depromeet.threedollar.domain.domain.storedelete.projection.StoreDeleteRequestWithCountProjection

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
        fun of(projection: StoreDeleteRequestWithCountProjection): ReportedStoresResponse {
            val response = ReportedStoresResponse(
                projection.storeId,
                projection.storeName,
                projection.latitude,
                projection.longitude,
                projection.type,
                projection.rating,
                projection.reportsCount
            )
            response.setBaseTime(projection.storeCreatedAt, projection.storeUpdatedAt)
            return response
        }
    }

}
