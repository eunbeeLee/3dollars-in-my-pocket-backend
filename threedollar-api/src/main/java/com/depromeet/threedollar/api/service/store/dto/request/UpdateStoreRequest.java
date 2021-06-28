package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStoreRequest {

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotBlank
	private String storeName;

	@NotNull
	private StoreType storeType;

	@NotNull
	private Set<DayOfTheWeek> appearanceDays = new HashSet<>();

	@NotNull
	private Set<PaymentMethodType> paymentMethods = new HashSet<>();

	@NotNull
	private List<MenuRequest> menu = new ArrayList<>();

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

	public List<Menu> toMenus(Store store) {
		return menu.stream()
				.map(menu -> menu.toEntity(store))
				.collect(Collectors.toList());
	}

}
