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

    public void renewStoreRating(Long storeId) {
        Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
        store.updateAverageRating(calculateAverageRating(storeId));
    }

    private double calculateAverageRating(Long storeId) {
        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);
        return reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0);
    }

}
