package com.depromeet.threedollar.api.controller.review;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.review.ReviewService;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.RetrieveMyReviewsRequest;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewDetailWithPaginationResponse;
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

    @Operation(summary = "리뷰를 등록합니다. 인증이 필요한 요청입니다", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @PostMapping("/api/v2/review/{storeId}")
    public ApiResponse<String> addStoreReview(@PathVariable Long storeId, @Valid @RequestBody AddReviewRequest request, @UserId Long userId) {
        reviewService.addReview(storeId, request, userId);
        return ApiResponse.SUCCESS;
    }

    @Operation(summary = "사용자가 작성한 리뷰를 수정합니다. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @PutMapping("/api/v2/review/{reviewId}")
    public ApiResponse<String> updateReview(@PathVariable Long reviewId, @Valid @RequestBody UpdateReviewRequest request, @UserId Long userId) {
        reviewService.updateReview(reviewId, request, userId);
        return ApiResponse.SUCCESS;
    }

    @Operation(summary = "사용자가 작성한 리뷰를 삭제합니다. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @DeleteMapping("/api/v2/review/{reviewId}")
    public ApiResponse<String> deleteReview(@PathVariable Long reviewId, @UserId Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return ApiResponse.SUCCESS;
    }

    @Operation(summary = "사용자가 작성한 리뷰를 조회합니다. 인증이 필요한 요청입니다.", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v2/review/user")
    public ApiResponse<ReviewDetailWithPaginationResponse> retrieveMyReviews(@Valid RetrieveMyReviewsRequest request, @UserId Long userId) {
        return ApiResponse.success(reviewService.retrieveMyReviews(request, userId));
    }

}
