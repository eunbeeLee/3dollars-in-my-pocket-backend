package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreator;

import java.util.List;

public interface ReviewRepositoryCustom {

	Review findReviewByIdAndUserId(Long reviewId, Long userId);

	List<ReviewWithCreator> findAllReviewWithCreatorByStoreId(Long storeId);

	List<Review> findAllReviewByStoreId(Long storeId);

	List<ReviewWithCreator> findAllReviewWithCreatorByUserId(Long userId);

}
