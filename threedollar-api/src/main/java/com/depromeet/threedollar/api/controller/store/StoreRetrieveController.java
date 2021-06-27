package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StoreRetrieveController {

	@Operation(summary = "특정 지역 주변의 가게들을 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/store/around")
	public void getAroundStores(@Valid RetrieveAroundStoresRequest request) {
		// TODO
	}

	@Operation(summary = "특정 가게의 정보를 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/store/detail")
	public void getStoreDetailInfo() {
		// TODO
	}

	@Operation(summary = "내가 작성한 가게들의 정보를 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/store/me")
	public void getMyStores() {
		// TODO
	}

	@Operation(summary = "거리순으로 특정 카테고리의 가게 정보를 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/store/category/distance")
	public void getStoresByCategory() {
		// TODO
	}

	@Operation(summary = "리뷰순으로 특정 카테고리의 가게 정보를 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/store/category/review")
	public void getStoresByReview() {
		// TODO
	}

}
