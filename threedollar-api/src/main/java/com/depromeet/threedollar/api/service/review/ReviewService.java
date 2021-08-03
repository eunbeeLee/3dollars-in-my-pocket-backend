package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.api.event.review.ReviewChangedEvent;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.RetrieveMyReviewsRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewInfoResponse;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewDetailWithPaginationResponse;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ApplicationEventPublisher eventPublisher;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewInfoResponse addReview(AddReviewRequest request, Long userId) {
        Review review = reviewRepository.save(request.toEntity(userId));
        eventPublisher.publishEvent(ReviewChangedEvent.of(request.getStoreId()));
        return ReviewInfoResponse.of(review);
    }

    @Transactional
    public ReviewInfoResponse updateReview(Long reviewId, UpdateReviewRequest request, Long userId) {
        Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
        review.update(request.getContent(), request.getRating());
        eventPublisher.publishEvent(ReviewChangedEvent.of(review.getStoreId()));
        return ReviewInfoResponse.of(review);
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
        review.delete();
        eventPublisher.publishEvent(ReviewChangedEvent.of(review.getStoreId()));
    }

    @Transactional(readOnly = true)
    public ReviewDetailWithPaginationResponse retrieveMyReviews(RetrieveMyReviewsRequest request, Long userId) {
        List<ReviewWithStoreAndCreatorProjection> currentAndNextScrollReviews = reviewRepository.findAllByUserIdWithScroll(userId, request.getCursor(), request.getSize() + 1);
        if (currentAndNextScrollReviews.size() <= request.getSize()) {
            return ReviewDetailWithPaginationResponse.newLastScroll(
                currentAndNextScrollReviews,
                request.getCachingTotalElements() == null ? reviewRepository.findCountsByUserId(userId) : request.getCachingTotalElements()
            );
        }

        List<ReviewWithStoreAndCreatorProjection> currentScrollReviews = currentAndNextScrollReviews.subList(0, request.getSize());
        return ReviewDetailWithPaginationResponse.of(
            currentScrollReviews,
            request.getCachingTotalElements() == null ? reviewRepository.findCountsByUserId(userId) : request.getCachingTotalElements(),
            currentScrollReviews.get(request.getSize() - 1).getId()
        );
    }

}
