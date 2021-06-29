package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.store.StoreRetrieveService;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveMyStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreGroupByCategoryRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreDetailInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreRetrieveController {

    private final StoreRetrieveService storeRetrieveService;

    @Operation(summary = "위도, 경도 주위 가게 목록을 조회합니다.")
    @GetMapping("/api/v2/store")
    public ApiResponse<List<StoreInfoResponse>> getNearStores(@Valid RetrieveAroundStoresRequest request) {
        return ApiResponse.success(storeRetrieveService.getNearStores(request));
    }

    @Operation(summary = "특정 가게의 정보를 조회합니다.")
    @GetMapping("/api/v2/store/detail")
    public ApiResponse<StoreDetailInfoResponse> getStoreDetailInfo(@Valid RetrieveStoreDetailInfoRequest request) {
        return ApiResponse.success(storeRetrieveService.getDetailStoreInfo(request));
    }

    @Operation(summary = "사용자가 작성한 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v2/store/user")
    public ApiResponse<MyStoresWithPaginationResponse> getMyStores(@Valid RetrieveMyStoresRequest request, @UserId Long userId) {
        return ApiResponse.success(storeRetrieveService.retrieveMyStores(request, userId));
    }

    @Operation(summary = "거리순으로 특정 카테고리의 가게 정보를 가져옵니다. ")
    @GetMapping("/api/v2/store/category/distance")
    public ApiResponse<StoresGroupByDistanceResponse> getStoresByCategory(@Valid RetrieveStoreGroupByCategoryRequest request) {
        return ApiResponse.success(storeRetrieveService.retrieveStoresGroupByDistance(request));
    }

    @Operation(summary = "리뷰순으로 특정 카테고리의 가게 정보를 가져옵니다.")
    @GetMapping("/api/v2/store/category/review")
    public ApiResponse<StoresGroupByReviewResponse> getStoresByReview(@Valid RetrieveStoreGroupByCategoryRequest request) {
        return ApiResponse.success(storeRetrieveService.retrieveStoresGroupByRating(request));
    }

}
