package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.utils.LocationDistanceUtils;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreInfoResponse {

	private Long id;
	private Double latitude;
	private Double longitude;
	private String storeName;
	private Double rating;
	private Integer distance;
	private final List<MenuCategoryType> categories = new ArrayList<>();

	public static StoreInfoResponse of(Store store, Double latitude, Double longitude) {
		StoreInfoResponse storeInfoResponse = new StoreInfoResponse(store.getId(), store.getLatitude(), store.getLongitude(), store.getStoreName(), store.getRating(),
				LocationDistanceUtils.getDistance(store.getLongitude(), store.getLatitude(), latitude, longitude));
		storeInfoResponse.categories.addAll(store.getMenuCategories());
		return storeInfoResponse;
	}

}
