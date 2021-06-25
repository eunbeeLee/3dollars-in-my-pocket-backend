package com.depromeet.threedollar.api.controller.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

	@Operation(summary = "특정 가게에 리뷰를 작성하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@PostMapping("/api/v2/review")
	public void addStoreReview() {

	}

	@Operation(summary = "특정 가게에 작성되어 있는 리뷰들을 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@GetMapping("/api/v2/review")
	public void getAllReviews() {

	}

	@Operation(summary = "내가 작성한 리뷰를 수정하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@PutMapping("/api/v2/review")
	public void updateReview() {

	}

	@Operation(summary = "내가 작성한 리뷰를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@DeleteMapping("/api/v2/review")
	public void deleteReview() {

	}

}
