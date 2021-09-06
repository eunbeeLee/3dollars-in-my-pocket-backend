package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.event.ReviewChangedEvent;
import com.depromeet.threedollar.api.service.store.StoreRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StoreEventListener {

    private final StoreRatingService storeRatingService;

    // TODO 가게 평균 리뷰 점수를 비동기적으로 처리해도 좋을듯??
    @EventListener
    public void renewStoreRating(ReviewChangedEvent event) {
        storeRatingService.renewStoreRating(event.getStore());
    }

}
