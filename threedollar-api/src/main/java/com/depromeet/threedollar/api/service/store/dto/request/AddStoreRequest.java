package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddStoreRequest {

	private Double latitude;

	private Double longitude;

	private String storeName;

	private List<MenuCategoryType> categories;

	private StoreType storeType;

	private Set<DayOfTheWeek> appearanceDays;

	private Set<PaymentMethodType> paymentMethods;

	private List<MenuRequest> menu;

	public Store toStore(Long userId) {
		Store store = Store.newInstance(userId, latitude, longitude, storeName, storeType);
		store.addPaymentMethods(paymentMethods);
		store.addAppearanceDays(appearanceDays);
		return store;
	}

}
