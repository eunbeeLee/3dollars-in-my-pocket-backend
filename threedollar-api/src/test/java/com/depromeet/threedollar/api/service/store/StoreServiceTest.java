package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import com.depromeet.threedollar.domain.domain.store.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoreServiceTest extends UserSetUpTest {

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private AppearanceDayRepository appearanceDayRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private MenuRepository menuRepository;

	@AfterEach
	void cleanUp() {
		super.cleanup();
		appearanceDayRepository.deleteAllInBatch();
		paymentMethodRepository.deleteAllInBatch();
		storeRepository.deleteAllInBatch();
		menuRepository.deleteAll();
	}

	@Test
	void 새로운_가게를_등록한다() {
		// given
		Double latitude = 10.5;
		Double longitude = 20.3;
		String storeName = "붕어빵";
		StoreType storeType = StoreType.STORE;
		Set<DayOfTheWeek> appearanceDays = Set.of(DayOfTheWeek.TUESDAY);
		Set<PaymentMethodType> paymentMethods = Set.of(PaymentMethodType.CARD);

		String menuName = "메뉴 이름";
		String price = "10000";
		MenuCategoryType type = MenuCategoryType.BUNGEOPPANG;
		List<MenuRequest> menu = Collections.singletonList(MenuRequest.of(menuName, price, type));

		AddStoreRequest request = AddStoreRequest.testBuilder()
				.latitude(latitude)
				.longitude(longitude)
				.storeName(storeName)
				.storeType(storeType)
				.appearanceDays(appearanceDays)
				.paymentMethods(paymentMethods)
				.menu(menu)
				.build();

		// when
		storeService.addStore(request, userId);

		// then
		List<Store> stores = storeRepository.findAll();
		assertThat(stores).hasSize(1);
		assertStore(stores.get(0), latitude, longitude, storeName, storeType);

		List<AppearanceDay> appearanceDayList = appearanceDayRepository.findAll();
		assertThat(appearanceDayList).hasSize(1);
		assertAppearanceDay(appearanceDayList.get(0), DayOfTheWeek.TUESDAY);

		List<PaymentMethod> paymentMethodsList = paymentMethodRepository.findAll();
		assertPaymentMethod(paymentMethodsList.get(0), PaymentMethodType.CARD);

		List<Menu> menus = menuRepository.findAll();
		assertThat(menus).hasSize(1);
		assertMenu(menus.get(0), menuName, price, type);
	}

	private void assertMenu(Menu menu, String menuName, String price, MenuCategoryType type) {
		assertThat(menu.getName()).isEqualTo(menuName);
		assertThat(menu.getPrice()).isEqualTo(price);
		assertThat(menu.getCategory()).isEqualTo(type);
	}

	private void assertPaymentMethod(PaymentMethod paymentMethod, PaymentMethodType type) {
		assertThat(paymentMethod.getMethod()).isEqualTo(type);
	}

	private void assertAppearanceDay(AppearanceDay appearanceDay, DayOfTheWeek day) {
		assertThat(appearanceDay.getDay()).isEqualTo(day);
	}

	private void assertStore(Store store, Double latitude, Double longitude, String storeName, StoreType storeType) {
		assertThat(store.getLatitude()).isEqualTo(latitude);
		assertThat(store.getLongitude()).isEqualTo(longitude);
		assertThat(store.getStoreName()).isEqualTo(storeName);
		assertThat(store.getStoreType()).isEqualTo(storeType);
	}

}