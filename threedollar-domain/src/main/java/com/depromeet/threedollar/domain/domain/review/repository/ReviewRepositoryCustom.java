package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.projection.ReviewWithWriterProjection;

import java.util.List;

public interface ReviewRepositoryCustom {

    Review findReviewByIdAndUserId(Long reviewId, Long userId);

    List<Review> findAllByStoreIdWithLock(Long storeId);

    long findCountsByUserId(Long userId);

    List<ReviewWithWriterProjection> findAllWithCreatorByStoreId(Long storeId);

    List<ReviewWithWriterProjection> findAllByUserIdWithScroll(Long userId, Long lastStoreId, int size);

}
