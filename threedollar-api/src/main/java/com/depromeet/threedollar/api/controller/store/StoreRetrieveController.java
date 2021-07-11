package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.service.store.StoreRetrieveService;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveMyStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreGroupByCategoryRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreDetailInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreRetrieveController {

    private final StoreRetrieveService storeRetrieveService;

    @ApiOperation("위도, 경도 주위 가게 목록을 조회합니다.")
    @GetMapping("/api/v2/stores/near")
    public ApiResponse<List<StoreInfoResponse>> getNearStores(@Valid RetrieveAroundStoresRequest request) {
        return ApiResponse.success(storeRetrieveService.getNearStores(request));
    }

    @ApiOperation("특정 가게의 정보를 조회합니다.")
    @GetMapping("/api/v2/store")
    public ApiResponse<StoreDetailInfoResponse> getStoreDetailInfo(@Valid RetrieveStoreDetailInfoRequest request) {
        return ApiResponse.success(storeRetrieveService.getDetailStoreInfo(request));
    }

    @ApiOperation("사용자가 작성한 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/api/v2/stores/me")
    public ApiResponse<MyStoresWithPaginationResponse> getMyStores(@Valid RetrieveMyStoresRequest request, @UserId Long userId) {
        return ApiResponse.success(storeRetrieveService.retrieveMyStores(request, userId));
    }

    @ApiOperation("거리순으로 특정 카테고리의 가게 정보를 가져옵니다.")
    @GetMapping("/api/v2/stores/distance")
    public ApiResponse<StoresGroupByDistanceResponse> getStoresByCategory(@Valid RetrieveStoreGroupByCategoryRequest request) {
        return ApiResponse.success(storeRetrieveService.retrieveStoresGroupByDistance(request));
    }

    @ApiOperation("리뷰순으로 특정 카테고리의 가게 정보를 가져옵니다.")
    @GetMapping("/api/v2/stores/review")
    public ApiResponse<StoresGroupByReviewResponse> getStoresByReview(@Valid RetrieveStoreGroupByCategoryRequest request) {
        return ApiResponse.success(storeRetrieveService.retrieveStoresGroupByRating(request));
    }

}
