package com.depromeet.threedollar.api.controller.review;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.service.review.ReviewService;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.RetrieveMyReviewsRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewInfoResponse;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewDetailWithPaginationResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation("[인증] 가게에 새로운 리뷰를 등록합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/api/v2/store/review")
    public ApiResponse<ReviewInfoResponse> addStoreReview(@Valid @RequestBody AddReviewRequest request,
                                                          @UserId Long userId) {
        return ApiResponse.success(reviewService.addReview(request, userId));
    }

    @ApiOperation("[인증] 사용자가 작성한 리뷰를 수정합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/api/v2/store/review/{reviewId}")
    public ApiResponse<ReviewInfoResponse> updateStoreReview(@PathVariable Long reviewId,
                                                             @Valid @RequestBody UpdateReviewRequest request,
                                                             @UserId Long userId) {
        return ApiResponse.success(reviewService.updateReview(reviewId, request, userId));
    }

    @ApiOperation("[인증] 사용자가 작성한 리뷰를 삭제합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("/api/v2/store/review/{reviewId}")
    public ApiResponse<String> deleteStoreReview(@PathVariable Long reviewId, @UserId Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation("[인증] 사용자가 작성한 리뷰를 전체 조회합니다. (페이지네이션)")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/api/v2/store/reviews/me")
    public ApiResponse<ReviewDetailWithPaginationResponse> retrieveMyStoreReviews(@Valid RetrieveMyReviewsRequest request,
                                                                                  @UserId Long userId) {
        return ApiResponse.success(reviewService.retrieveMyReviews(request, userId));
    }

}
