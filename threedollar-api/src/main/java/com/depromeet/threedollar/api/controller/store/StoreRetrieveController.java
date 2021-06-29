package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.store.StoreRetrieveService;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreGroupByCategoryRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDetailInfoResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoresGroupByDistanceResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoresGroupByReviewResponse;
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

	@Operation(summary = "특정 지역 주변의 가게들을 조회하는 API")
	@GetMapping("/api/v2/store")
	public ApiResponse<List<StoreInfoResponse>> getAroundStores(@Valid RetrieveAroundStoresRequest request) {
		return ApiResponse.success(storeRetrieveService.getAllStoresLessThanDistance(request));
	}

	@Operation(summary = "특정 가게의 정보를 조회하는 API")
	@GetMapping("/api/v2/store/detail")
	public ApiResponse<StoreDetailInfoResponse> getStoreDetailInfo(@Valid RetrieveStoreInfoRequest request) {
		return ApiResponse.success(storeRetrieveService.getDetailStoreInfo(request));
	}

	@Operation(summary = "내가 작성한 가게들의 정보를 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/store/user")
	public void getMyStores() {
		// TODO
	}

	@Operation(summary = "거리순으로 특정 카테고리의 가게 정보를 조회하는 API")
	@GetMapping("/api/v2/store/category/distance")
	public ApiResponse<StoresGroupByDistanceResponse> getStoresByCategory(@Valid RetrieveStoreGroupByCategoryRequest request) {
		return ApiResponse.success(storeRetrieveService.retrieveStoresGroupByDistance(request));
	}

	@Operation(summary = "리뷰순으로 특정 카테고리의 가게 정보를 조회하는 API")
	@GetMapping("/api/v2/store/category/review")
	public ApiResponse<StoresGroupByReviewResponse> getStoresByReview(@Valid RetrieveStoreGroupByCategoryRequest request) {
		return ApiResponse.success(storeRetrieveService.retrieveStoresGroupByRating(request));
	}

}
