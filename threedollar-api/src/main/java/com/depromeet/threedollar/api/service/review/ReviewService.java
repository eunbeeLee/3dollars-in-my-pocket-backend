package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.api.event.review.ReviewChangedEvent;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.RetrieveMyReviewsRequest;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewDetailWithPaginationResponse;
import com.depromeet.threedollar.api.service.store.StoreServiceUtils;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithStoreAndCreatorDto;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ApplicationEventPublisher eventPublisher;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void addReview(Long storeId, AddReviewRequest request, Long userId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId);
        reviewRepository.save(request.toEntity(storeId, userId));
        eventPublisher.publishEvent(ReviewChangedEvent.of(storeId));
    }

    @Transactional
    public void updateReview(Long reviewId, UpdateReviewRequest request, Long userId) {
        Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
        review.update(request.getContent(), request.getRating());
        eventPublisher.publishEvent(ReviewChangedEvent.of(review.getStoreId()));
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
        review.delete();
        eventPublisher.publishEvent(ReviewChangedEvent.of(review.getStoreId()));
    }

    @Transactional(readOnly = true)
    public ReviewDetailWithPaginationResponse retrieveMyReviews(RetrieveMyReviewsRequest request, Long userId) {
        Page<ReviewWithStoreAndCreatorDto> result = reviewRepository.findAllWithCreatorByUserId(userId, PageRequest.of(request.getPage(), request.getSize()));
        return ReviewDetailWithPaginationResponse.of(result);
    }

}
