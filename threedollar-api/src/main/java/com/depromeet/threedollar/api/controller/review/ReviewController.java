package com.depromeet.threedollar.api.controller.review;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.review.ReviewService;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "특정 가게에 리뷰를 작성하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PostMapping("/api/v2/review")
	public ApiResponse<String> addStoreReview(@Valid @RequestBody AddReviewRequest request, @UserId Long userId) {
		reviewService.addReview(request, userId);
		return ApiResponse.SUCCESS;
	}

	@Operation(summary = "특정 리뷰를 상세조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@GetMapping("/api/v2/review/{reviewId}")
	public void getReview(@PathVariable Long reviewId) {
		// TODO
	}

	@Operation(summary = "내가 작성한 리뷰를 수정하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PutMapping("/api/v2/review/{reviewId}")
	public ApiResponse<String> updateReview(@PathVariable Long reviewId, @Valid @RequestBody UpdateReviewRequest request, @UserId Long userId) {
		reviewService.updateReview(reviewId, request, userId);
		return ApiResponse.SUCCESS;
	}

	@Operation(summary = "내가 작성한 리뷰를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@DeleteMapping("/api/v2/review/{reviewId}")
	public ApiResponse<String> deleteReview(@PathVariable Long reviewId, @UserId Long userId) {
		reviewService.deleteReview(reviewId, userId);
		return ApiResponse.SUCCESS;
	}

}
