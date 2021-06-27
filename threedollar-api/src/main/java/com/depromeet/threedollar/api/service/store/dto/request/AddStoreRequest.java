package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddStoreRequest {

	private Double latitude;

	private Double longitude;

	private String storeName;

	private StoreType storeType;

	private Set<DayOfTheWeek> appearanceDays;

	private Set<PaymentMethodType> paymentMethods;

	private List<MenuRequest> menu;

	@Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
	public AddStoreRequest(Double latitude, Double longitude, String storeName, StoreType storeType,
						   Set<DayOfTheWeek> appearanceDays, Set<PaymentMethodType> paymentMethods, List<MenuRequest> menu) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.storeName = storeName;
		this.storeType = storeType;
		this.appearanceDays = appearanceDays;
		this.paymentMethods = paymentMethods;
		this.menu = menu;
	}

	public Store toStore(Long userId) {
		Store store = Store.newInstance(userId, latitude, longitude, storeName, storeType);
		store.addPaymentMethods(paymentMethods);
		store.addAppearanceDays(appearanceDays);
		return store;
	}

}
