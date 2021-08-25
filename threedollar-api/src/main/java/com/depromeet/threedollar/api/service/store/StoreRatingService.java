package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StoreRatingService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void renewStoreRating(Store store) {
        double average = calculateAverageRating(store.getId());
        store.updateAverageRating(average);
    }

    private double calculateAverageRating(Long storeId) {
        return reviewRepository.findAllByStoreIdWithLock(storeId).stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0);
    }

}
