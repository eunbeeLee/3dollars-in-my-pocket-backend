package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithCreatorProjection;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {

    Review findReviewByIdAndUserId(Long reviewId, Long userId);

    List<ReviewWithCreatorProjection> findAllWithCreatorByStoreId(Long storeId);

    List<Review> findAllByStoreId(Long storeId);

    Page<ReviewWithStoreAndCreatorProjection> findAllWithCreatorByUserId(Long userId, Pageable pageable);

}
