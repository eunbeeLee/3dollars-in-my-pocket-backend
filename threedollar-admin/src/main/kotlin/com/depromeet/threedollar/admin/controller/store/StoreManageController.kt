package com.depromeet.threedollar.admin.controller.store

import com.depromeet.threedollar.admin.common.dto.ApiResponse
import com.depromeet.threedollar.admin.service.store.StoreManageService
import com.depromeet.threedollar.admin.service.store.dto.request.RetrieveReportedStoresRequest
import com.depromeet.threedollar.admin.service.store.dto.response.ReportedStoresResponse
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreManageController(
    private val storeManageService: StoreManageService
) {

    @ApiOperation("신고가 기준치 이상 요청된 가게들을 조회한다.")
    @GetMapping("/admin/v1/stores/report")
    fun retrieveReportedStores(
        request: RetrieveReportedStoresRequest
    ): ApiResponse<List<ReportedStoresResponse>> {
        return ApiResponse.success(storeManageService.retrieveReportedStores(request))
    }

}
