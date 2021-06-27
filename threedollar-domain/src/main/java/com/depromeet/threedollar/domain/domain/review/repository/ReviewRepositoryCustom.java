package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;

public interface ReviewRepositoryCustom {

	Review findReviewById(Long reviewId);

	Review findReviewByIdAndUserId(Long reviewId, Long userId);

}
