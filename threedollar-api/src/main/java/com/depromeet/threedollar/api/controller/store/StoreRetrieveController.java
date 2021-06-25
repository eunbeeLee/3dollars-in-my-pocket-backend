package com.depromeet.threedollar.api.controller.store;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreRetrieveController {

	/**
	 * 내 주변의 가게들을 조회하는 API
	 */
	@GetMapping("/api/v1/store/around")
	public void getAroundStores() {

	}

	/**
	 * 특정 가게의 정보를 조회하는 API
	 */
	@GetMapping("/api/v1/store/detail")
	public void getStoreDetailInfo() {

	}

	/**
	 * 내가 작성한 가게들의 정보를 조회하는 API
	 */
	@GetMapping("/api/v1/store/me")
	public void getMyStores() {

	}

	/**
	 * 거리순으로 특정 카테고리의 가게 정보를 조회하는 API
	 */
	@GetMapping("/api/v1/store/category/distance")
	public void getStoresByCategory() {

	}

	/**
	 * 리뷰순으로 특정 카테고리의 가게 정보를 조회하는 API
	 */
	@GetMapping("/api/v1/store/category/review")
	public void getStoresByReview() {

	}

}
