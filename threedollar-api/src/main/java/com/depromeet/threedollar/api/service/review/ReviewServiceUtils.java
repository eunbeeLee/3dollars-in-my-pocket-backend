package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.common.exception.model.NotFoundException;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.depromeet.threedollar.common.exception.ErrorCode.NOT_FOUND_REVIEW_EXCEPTION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReviewServiceUtils {

    static Review findReviewByIdAndUserId(ReviewRepository reviewRepository, Long reviewId, Long userId) {
        Review review = reviewRepository.findReviewByIdAndUserId(reviewId, userId);
        if (review == null) {
            throw new NotFoundException(String.format("해당하는 리뷰 (%s)는 존재하지 않습니다", reviewId), NOT_FOUND_REVIEW_EXCEPTION);
        }
        return review;
    }

}
