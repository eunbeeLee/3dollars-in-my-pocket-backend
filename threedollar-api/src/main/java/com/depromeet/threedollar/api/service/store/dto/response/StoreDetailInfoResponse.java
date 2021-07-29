package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.common.utils.LocationDistanceUtils;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
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
public class StoreDetailInfoResponse extends AuditingTimeResponse {

    private Long storeId;

    private Double latitude;

    private Double longitude;

    private String storeName;

    private List<MenuCategoryType> categories;

    private StoreType storeType;

    private Double rating;

    private Integer distance;

    private UserInfoResponse user;

    private final Set<DayOfTheWeek> appearanceDays = new HashSet<>();

    private final Set<PaymentMethodType> paymentMethods = new HashSet<>();

    private final List<StoreImageResponse> image = new ArrayList<>();

    private final List<MenuResponse> menu = new ArrayList<>();

    private final List<ReviewResponse> review = new ArrayList<>();

    @Builder
    private StoreDetailInfoResponse(Long storeId, Double latitude, Double longitude, String storeName, List<MenuCategoryType> categories, StoreType storeType, Double rating, Integer distance, UserInfoResponse user) {
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.categories = categories;
        this.storeType = storeType;
        this.rating = rating;
        this.distance = distance;
        this.user = user;
    }

    public static StoreDetailInfoResponse of(Store store, List<StoreImageResponse> imageResponses, Double latitude,
                                             Double longitude, User user, List<ReviewResponse> reviewResponses) {
        StoreDetailInfoResponse response = StoreDetailInfoResponse.builder()
            .storeId(store.getId())
            .latitude(store.getLatitude())
            .longitude(store.getLongitude())
            .storeName(store.getName())
            .categories(store.getMenuCategories())
            .storeType(store.getType())
            .rating(store.getRating())
            .distance(LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude))
            .user(UserInfoResponse.of(Objects.requireNonNullElseGet(user, User::deletedUser))) // 회원탈퇴한 제보자인경우 사라진 제보자로 보이게 하기 위함
            .build();
        response.appearanceDays.addAll(store.getAppearanceDaysType());
        response.paymentMethods.addAll(store.getPaymentMethodsType());
        response.image.addAll(imageResponses);
        response.menu.addAll(store.getMenus().stream()
            .map(MenuResponse::of)
            .collect(Collectors.toList()));
        response.review.addAll(reviewResponses);
        response.setBaseTime(store);
        return response;
    }

}
