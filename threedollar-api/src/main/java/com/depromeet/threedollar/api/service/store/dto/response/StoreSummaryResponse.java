package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreSummaryResponse extends AuditingTimeResponse {

    private Long storeId;
    private Double latitude;
    private Double longitude;
    private String storeName;
    private StoreType storeType;
    private Double rating;
    private final List<MenuCategoryType> categories = new ArrayList<>();
    private final Set<DayOfTheWeek> appearanceDays = new HashSet<>();
    private final Set<PaymentMethodType> paymentMethods = new HashSet<>();

    @Builder
    private StoreSummaryResponse(Long storeId, Double latitude, Double longitude, String storeName, StoreType storeType, Double rating) {
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.storeType = storeType;
        this.rating = rating;
    }

    public static StoreSummaryResponse of(Store store) {
        StoreSummaryResponse response = StoreSummaryResponse.builder()
            .storeId(store.getId())
            .latitude(store.getLatitude())
            .longitude(store.getLongitude())
            .storeName(store.getName())
            .storeType(store.getType())
            .rating(store.getRating())
            .build();
        response.categories.addAll(store.getMenuCategories());
        response.appearanceDays.addAll(store.getAppearanceDaysType());
        response.paymentMethods.addAll(store.getPaymentMethodsType());
        response.setBaseTime(store);
        return response;
    }

}
