package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithWriterProjection;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;

import java.util.List;

public interface ReviewRepositoryCustom {

    Review findReviewByIdAndUserId(Long reviewId, Long userId);

    List<ReviewWithWriterProjection> findAllWithCreatorByStoreId(Long storeId);

    List<Review> findAllByStoreId(Long storeId);

    long findCountsByUserId(Long userId);

    List<ReviewWithStoreAndCreatorProjection> findAllByUserIdWithScroll(Long userId, Long lastStoreId, int size);

}
