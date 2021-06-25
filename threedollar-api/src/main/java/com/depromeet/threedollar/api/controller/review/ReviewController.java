package com.depromeet.threedollar.api.controller.review;

import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

	/**
	 * 특정 가게에 리뷰를 작성하는 API
	 */
	@PostMapping("/api/v1/review")
	public void addStoreReview() {

	}

	/**
	 * 특정 가게에 작성되어있는 리뷰들을 조회하는 API
	 */
	@GetMapping("/api/v1/review")
	public void getAllReviews() {

	}

	/**
	 * 내가 작성한 리뷰를 수정하는 API
	 */
	@PutMapping("/api/v1/review")
	public void updateReview() {

	}

	/**
	 * 내가 작성한 리뷰를 삭제하는 API
	 */
	@DeleteMapping("/api/v1/review")
	public void deleteReview() {

	}

}
