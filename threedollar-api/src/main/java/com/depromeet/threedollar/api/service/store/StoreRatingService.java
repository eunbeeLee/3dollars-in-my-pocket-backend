package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreRatingService {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    public void renewRating(Long storeId) {
        Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
        List<Review> reviews = reviewRepository.findAllReviewByStoreId(storeId);
        double average = reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0);
        store.updateRating(average);
    }

}
