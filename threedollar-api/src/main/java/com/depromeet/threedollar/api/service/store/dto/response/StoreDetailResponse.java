package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewWithWriterResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.common.utils.LocationDistanceUtils;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithWriterProjection;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import com.depromeet.threedollar.domain.domain.user.User;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreDetailResponse extends AuditingTimeResponse {

    private Long storeId;
    private Double latitude;
    private Double longitude;
    private String storeName;
    private StoreType storeType;
    private Double rating;
    private Integer distance;
    private UserInfoResponse user;
    private final List<MenuCategoryType> categories = new ArrayList<>();
    private final Set<DayOfTheWeek> appearanceDays = new HashSet<>();
    private final Set<PaymentMethodType> paymentMethods = new HashSet<>();
    private final List<StoreImageResponse> images = new ArrayList<>();
    private final List<MenuResponse> menus = new ArrayList<>();
    private final List<ReviewWithWriterResponse> reviews = new ArrayList<>();

    @Builder
    private StoreDetailResponse(Long storeId, Double latitude, Double longitude, String storeName, StoreType storeType,
                                Double rating, Integer distance, UserInfoResponse user) {
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.storeType = storeType;
        this.rating = rating;
        this.distance = distance;
        this.user = user;
    }

    public static StoreDetailResponse of(Store store, List<StoreImageResponse> imageResponses, Double latitude,
                                         Double longitude, User user, List<ReviewWithWriterProjection> reviews) {
        StoreDetailResponse response = StoreDetailResponse.builder()
            .storeId(store.getId())
            .latitude(store.getLatitude())
            .longitude(store.getLongitude())
            .storeName(store.getName())
            .storeType(store.getType())
            .rating(store.getRating())
            .distance(LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude))
            .user(UserInfoResponse.of(user))
            .build();
        response.categories.addAll(store.getMenuCategories());
        response.appearanceDays.addAll(store.getAppearanceDaysType());
        response.paymentMethods.addAll(store.getPaymentMethodsType());
        response.images.addAll(imageResponses);
        response.menus.addAll(store.getMenus().stream()
            .map(MenuResponse::of)
            .collect(Collectors.toList()));
        response.reviews.addAll(reviews.stream()
            .map(ReviewWithWriterResponse::of)
            .collect(Collectors.toList())
        );
        response.setBaseTime(store);
        return response;
    }

}
