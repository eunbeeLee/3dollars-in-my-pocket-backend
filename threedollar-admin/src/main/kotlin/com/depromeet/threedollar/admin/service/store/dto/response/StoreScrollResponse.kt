package com.depromeet.threedollar.admin.service.store.dto.response

import com.depromeet.threedollar.domain.domain.store.Store

data class StoreScrollResponse(
    val contents: List<StoreInfoResponse>,
    val nextCursor: Long
) {

    companion object {
        fun of(stores: List<Store>, nextCursor: Long): StoreScrollResponse {
            return StoreScrollResponse(stores.map { StoreInfoResponse.of(it) }, nextCursor)
        }

        fun lastCursor(stores: List<Store>): StoreScrollResponse {
            return of(stores, -1L)
        }
    }

}
