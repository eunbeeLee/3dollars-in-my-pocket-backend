package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.application.common.dto.AuditingTimeResponse;
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
    private double latitude;
    private double longitude;
    private String storeName;
    private double rating;
    private Integer distance;
    private final List<MenuCategoryType> categories = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private StoreInfoResponse(Long storeId, double latitude, double longitude, String storeName, double rating, Integer distance) {
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.rating = rating;
        this.distance = distance;
    }

    public static StoreInfoResponse of(Store store, double latitude, double longitude) {
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
        StoreInfoResponse response = StoreInfoResponse.builder()
            .storeId(store.getId())
            .latitude(store.getLatitude())
            .longitude(store.getLongitude())
            .storeName(store.getName())
            .rating(store.getRating())
            .distance(null)
            .build();
        response.categories.addAll(store.getMenuCategories());
        response.setBaseTime(store);
        return response;
    }

}
