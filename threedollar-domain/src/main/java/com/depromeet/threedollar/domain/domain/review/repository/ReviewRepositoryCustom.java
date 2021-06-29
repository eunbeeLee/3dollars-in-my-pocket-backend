package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreatorDto;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithStoreAndCreatorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {

    Review findReviewByIdAndUserId(Long reviewId, Long userId);

    List<ReviewWithCreatorDto> findAllWithCreatorByStoreId(Long storeId);

    List<Review> findAllByStoreId(Long storeId);

    Page<ReviewWithStoreAndCreatorDto> findAllWithCreatorByUserId(Long userId, Pageable pageable);

}
