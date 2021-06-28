package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStoreRequest {

	private Double latitude;

	private Double longitude;

	private String storeName;

	private StoreType storeType;

	private Set<DayOfTheWeek> appearanceDays;

	private Set<PaymentMethodType> paymentMethods;

	private List<MenuRequest> menu;

	@Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
	public UpdateStoreRequest(Double latitude, Double longitude, String storeName, StoreType storeType,
							  Set<DayOfTheWeek> appearanceDays, Set<PaymentMethodType> paymentMethods, List<MenuRequest> menu) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.storeName = storeName;
		this.storeType = storeType;
		this.appearanceDays = appearanceDays;
		this.paymentMethods = paymentMethods;
		this.menu = menu;
	}

}
