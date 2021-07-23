package com.depromeet.threedollar.admin.service.store

import com.depromeet.threedollar.admin.service.store.dto.request.RetrieveReportedStoresRequest
import com.depromeet.threedollar.admin.service.store.dto.response.ReportedStoresResponse
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreManageService(
    private val storeDeleteRequestRepository: StoreDeleteRequestRepository
) {

    @Transactional(readOnly = true)
    fun retrieveReportedStores(request: RetrieveReportedStoresRequest): List<ReportedStoresResponse> {
        return storeDeleteRequestRepository.findStoreHasDeleteRequestMoreThanCnt(request.minCount).asSequence()
            .map { ReportedStoresResponse.of(it) }
            .toList()
    }

}
