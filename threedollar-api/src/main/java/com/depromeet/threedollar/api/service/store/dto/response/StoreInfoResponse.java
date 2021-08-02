package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.common.utils.LocationDistanceUtils;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreInfoResponse extends AuditingTimeResponse {

    private Long storeId;
    private Double latitude;
    private Double longitude;
    private String storeName;
    private Double rating;
    private Integer distance;
    private final List<MenuCategoryType> categories = new ArrayList<>();

    @Builder
    private StoreInfoResponse(Long storeId, Double latitude, Double longitude, String storeName,
                              Double rating, Integer distance) {
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.rating = rating;
        this.distance = distance;
    }

    public static StoreInfoResponse of(Store store, Double latitude, Double longitude) {
        StoreInfoResponse response = StoreInfoResponse.builder()
            .storeId(store.getId())
            .latitude(store.getLatitude())
            .longitude(store.getLongitude())
            .storeName(store.getName())
            .rating(store.getRating())
            .distance(LocationDistanceUtils.getDistance(latitude, longitude, store.getLatitude(), store.getLongitude()))
            .build();
        response.categories.addAll(store.getMenuCategories());
        response.setBaseTime(store);
        return response;
    }

    public static StoreInfoResponse of(Store store) {
        StoreInfoResponse storeInfoResponse = new StoreInfoResponse(store.getId(), store.getLatitude(), store.getLongitude(), store.getName(), store.getRating(), null);
        storeInfoResponse.categories.addAll(store.getMenuCategories());
        storeInfoResponse.setBaseTime(store);
        return storeInfoResponse;
    }

}
