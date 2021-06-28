package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.api.utils.LocationDistanceUtils;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import com.depromeet.threedollar.domain.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreDetailInfoResponse {

	private Long id;

	private Double latitude;

	private Double longitude;

	private String storeName;

	private List<MenuCategoryType> categories;

	private StoreType storeType;

	private Float rating;

	private Integer distance;

	private Set<DayOfTheWeek> appearanceDays;

	private Set<PaymentMethodType> paymentMethods;

	@JsonProperty("image")
	private List<StoreImageResponse> imageResponses;

	@JsonProperty("menu")
	private List<MenuResponse> menuResponses = new ArrayList<>();
	//
//	@JsonProperty("review")
//	private List<ReviewDetailResponse> reviewDetailResponses = new ArrayList<>();
//
//
//
	private UserInfoResponse user;

	public static StoreDetailInfoResponse of(Store store, List<StoreImageResponse> imageResponses, Double latitude, Double longitude, User user) {
		return new StoreDetailInfoResponse(store.getId(), store.getLatitude(), store.getLongitude(), store.getStoreName(),
				store.getMenuCategories(), store.getStoreType(), store.getRating(), LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude),
				store.getAppearanceDaysType(), store.getPaymentMethodsType(), imageResponses,
				store.getMenus().stream()
						.map(MenuResponse::of)
						.collect(Collectors.toList()),
				UserInfoResponse.of(user)
		);
	}

}
