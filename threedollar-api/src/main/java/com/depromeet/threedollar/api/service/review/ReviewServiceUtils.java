package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewServiceUtils {

    static Review findReviewByIdAndUserId(ReviewRepository reviewRepository, Long reviewId, Long userId) {
        Review review = reviewRepository.findReviewByIdAndUserId(reviewId, userId);
        if (review == null) {
            throw new NotFoundException(String.format("해당하는 리뷰 (%s)는 존재하지 않습니다", reviewId), ErrorCode.NOT_FOUND_REVIEW_EXCEPTION);
        }
        return review;
    }

}
