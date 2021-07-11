package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.common.dto.AudtingTimeResponse;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreDetailInfoResponse extends AudtingTimeResponse {

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

    public static StoreDetailInfoResponse of(Store store, List<StoreImageResponse> imageResponses, Double latitude, Double longitude, User user, List<ReviewResponse> reviewResponses) {
        StoreDetailInfoResponse response = new StoreDetailInfoResponse(store.getId(), store.getLatitude(), store.getLongitude(), store.getStoreName(),
            store.getMenuCategories(), store.getStoreType(), store.getRating(), LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude),
            UserInfoResponse.of(user));
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
