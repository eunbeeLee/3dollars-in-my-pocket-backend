package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStoreRequest {

	private Long storeId;

	private Double latitude;

	private Double longitude;

	private String storeName;

	private StoreType storeType;

	private Set<DayOfTheWeek> appearanceDays;

	private Set<PaymentMethodType> paymentMethods;

	private List<MenuRequest> menu;

}
