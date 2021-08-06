package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.api.service.review.dto.request.RetrieveMyReviewsRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewScrollResponse;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.review.projection.ReviewWithStoreAndCreatorProjection;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewRetrieveService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public ReviewScrollResponse retrieveMyReviews(RetrieveMyReviewsRequest request, Long userId) {
        List<ReviewWithStoreAndCreatorProjection> currentAndNextScrollReviews =
            reviewRepository.findAllByUserIdWithScroll(userId, request.getCursor(), request.getSize() + 1);
        Map<Long, Store> cachedStores = findStoreMaps(currentAndNextScrollReviews);

        if (currentAndNextScrollReviews.size() <= request.getSize()) {
            return ReviewScrollResponse.newLastScroll(
                currentAndNextScrollReviews,
                Objects.requireNonNullElseGet(request.getCachingTotalElements(), () -> reviewRepository.findCountsByUserId(userId)),
                cachedStores
            );
        }

        List<ReviewWithStoreAndCreatorProjection> currentScrollReviews = currentAndNextScrollReviews.subList(0, request.getSize());
        return ReviewScrollResponse.of(
            currentScrollReviews,
            Objects.requireNonNullElseGet(request.getCachingTotalElements(), () -> reviewRepository.findCountsByUserId(userId)),
            cachedStores,
            currentScrollReviews.get(request.getSize() - 1).getId()
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
