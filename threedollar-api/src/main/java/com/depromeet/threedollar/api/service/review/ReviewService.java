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
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ApplicationEventPublisher eventPublisher;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewInfoResponse addReview(AddReviewRequest request, Long userId) {
        Review review = reviewRepository.save(request.toEntity(userId));
        eventPublisher.publishEvent(ReviewChangedEvent.of(request.getStoreId()));
        return ReviewInfoResponse.of(review);
    }

    @Transactional
    public ReviewInfoResponse updateReview(Long reviewId, UpdateReviewRequest request, Long userId) {
        Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
        review.update(request.getContents(), request.getRating());
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

        Map<Long, Store> cachedStores = findStoreMaps(currentAndNextScrollReviews);

        if (currentAndNextScrollReviews.size() <= request.getSize()) {
            return ReviewDetailWithPaginationResponse.newLastScroll(
                currentAndNextScrollReviews,
                request.getCachingTotalElements() == null ? reviewRepository.findCountsByUserId(userId) : request.getCachingTotalElements(),
                cachedStores
            );
        }

        List<ReviewWithStoreAndCreatorProjection> currentScrollReviews = currentAndNextScrollReviews.subList(0, request.getSize());
        return ReviewDetailWithPaginationResponse.of(
            currentScrollReviews,
            request.getCachingTotalElements() == null ? reviewRepository.findCountsByUserId(userId) : request.getCachingTotalElements(),
            currentScrollReviews.get(request.getSize() - 1).getId(),
            cachedStores
        );
    }

    private Map<Long, Store> findStoreMaps(List<ReviewWithStoreAndCreatorProjection> reviews) {
        List<Long> storeIds = reviews.stream()
            .map(ReviewWithStoreAndCreatorProjection::getStoreId)
            .collect(Collectors.toList());

        return storeRepository.findAllByIds(storeIds).stream()
            .collect(Collectors.toMap(Store::getId, store -> store));
    }

}
