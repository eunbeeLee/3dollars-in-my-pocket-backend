package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.common.exception.model.notfound.NotFoundReviewException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReviewServiceUtils {

    static Review findReviewByIdAndUserId(ReviewRepository reviewRepository, Long reviewId, Long userId) {
        Review review = reviewRepository.findReviewByIdAndUserId(reviewId, userId);
        if (review == null) {
            throw new NotFoundReviewException(String.format("해당하는 리뷰 (%s)는 존재하지 않습니다", reviewId));
        }
        return review;
    }

}
