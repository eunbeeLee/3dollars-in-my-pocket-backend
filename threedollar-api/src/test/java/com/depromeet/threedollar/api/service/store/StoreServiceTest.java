package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.MenuRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.common.Location;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import com.depromeet.threedollar.domain.domain.store.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        menuRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
    }

    @Test
    void 새로운_가게를_등록하면_Store_DB_데이터가_추가된다() {
        // given
        Double latitude = 34.0;
        Double longitude = 130.0;

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
        assertStore(stores.get(0), latitude, longitude, storeName, storeType, userId);
    }

    @Test
    void 새로운_가게를_등록하면_새로운_게시일도_함께_추가된다() {
        // given
        Set<DayOfTheWeek> appearanceDays = Set.of(DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY);

        AddStoreRequest request = AddStoreRequest.testBuilder()
            .latitude(34.0)
            .longitude(130.0)
            .storeName("붕어빵")
            .storeType(StoreType.STORE)
            .appearanceDays(appearanceDays)
            .paymentMethods(Collections.emptySet())
            .menu(Collections.emptyList())
            .build();

        // when
        storeService.addStore(request, userId);

        // then
        List<AppearanceDay> appearanceDayList = appearanceDayRepository.findAll();
        assertThat(appearanceDayList).hasSize(2);
        assertThat(getDayOfTheWeeks(appearanceDayList)).containsAll(Arrays.asList(DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY));
    }

    @Test
    void 새로운_가게를_등록하면_결제_방법도_함께_추가된다() {
        // given
        Set<PaymentMethodType> paymentMethods = Set.of(PaymentMethodType.CARD, PaymentMethodType.CASH);

        AddStoreRequest request = AddStoreRequest.testBuilder()
            .latitude(34.0)
            .longitude(130.0)
            .storeName("붕어빵")
            .storeType(StoreType.STORE)
            .appearanceDays(Collections.emptySet())
            .paymentMethods(paymentMethods)
            .menu(Collections.emptyList())
            .build();

        // when
        storeService.addStore(request, userId);

        // then
        List<PaymentMethod> paymentMethodsList = paymentMethodRepository.findAll();
        assertThat(paymentMethodsList).hasSize(2);
        assertThat(getPaymentMethodTypes(paymentMethodsList)).containsAll(Arrays.asList(PaymentMethodType.CARD, PaymentMethodType.CASH));
    }

    @Test
    void 새로운_가게를_등록하면_메뉴들도_함께_추가된다() {
        // given
        String menuName = "메뉴 이름";
        String price = "10000";
        MenuCategoryType type = MenuCategoryType.BUNGEOPPANG;
        List<MenuRequest> menus = Collections.singletonList(MenuRequest.of(menuName, price, type));

        AddStoreRequest request = AddStoreRequest.testBuilder()
            .latitude(34.0)
            .longitude(130.0)
            .storeName("붕어빵")
            .storeType(StoreType.STORE)
            .appearanceDays(Collections.emptySet())
            .paymentMethods(Collections.emptySet())
            .menu(menus)
            .build();

        // when
        storeService.addStore(request, userId);

        // then
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(1);
        assertMenu(menuList.get(0), menuName, price, type);
    }

    @Test
    void 가게의_정보를_수정하며_기존_가게정보_데이터들이_수정된다() {
        // given
        Store store = StoreCreator.create(userId, "storeName");
        storeRepository.save(store);

        Double latitude = 34.0;
        Double longitude = 130.0;
        String storeName = "붕어빵";
        StoreType storeType = StoreType.STORE;
        Set<DayOfTheWeek> appearanceDays = Set.of(DayOfTheWeek.TUESDAY);
        Set<PaymentMethodType> paymentMethods = Set.of(PaymentMethodType.CARD);

        String menuName = "메뉴 이름";
        String price = "10000";
        MenuCategoryType type = MenuCategoryType.BUNGEOPPANG;
        List<MenuRequest> menu = Collections.singletonList(MenuRequest.of(menuName, price, type));

        UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
            .latitude(latitude)
            .longitude(longitude)
            .storeName(storeName)
            .storeType(storeType)
            .appearanceDays(appearanceDays)
            .paymentMethods(paymentMethods)
            .menu(menu)
            .build();

        // when
        storeService.updateStore(store.getId(), request, userId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertStore(stores.get(0), latitude, longitude, storeName, storeType, userId);

        List<AppearanceDay> appearanceDayList = appearanceDayRepository.findAll();
        assertThat(appearanceDayList).hasSize(1);
        assertAppearanceDay(appearanceDayList.get(0), DayOfTheWeek.TUESDAY, stores.get(0).getId());

        List<PaymentMethod> paymentMethodsList = paymentMethodRepository.findAll();
        assertThat(paymentMethodsList).hasSize(1);
        assertPaymentMethod(paymentMethodsList.get(0), PaymentMethodType.CARD, stores.get(0).getId());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertMenu(menus.get(0), menuName, price, type);
    }

    @MethodSource("source_update_payments")
    @ParameterizedTest
    void 가게의_결제방법을_수정한다(Set<PaymentMethodType> paymentMethodTypes) {
        // given
        Store store = StoreCreator.create(userId, "storeName");
        store.addPaymentMethods(Set.of(PaymentMethodType.CARD));
        storeRepository.save(store);

        UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
            .latitude(34.0)
            .longitude(130.0)
            .storeName("붕어빵")
            .storeType(StoreType.STORE)
            .appearanceDays(Collections.emptySet())
            .paymentMethods(paymentMethodTypes)
            .menu(Collections.emptyList())
            .build();

        // when
        storeService.updateStore(store.getId(), request, userId);

        // then
        List<PaymentMethod> paymentMethodsList = paymentMethodRepository.findAll();
        assertThat(paymentMethodsList).hasSize(paymentMethodTypes.size());
        assertThat(getPaymentMethodTypes(paymentMethodsList)).containsAll(paymentMethodTypes);
    }

    private static Stream<Arguments> source_update_payments() {
        return Stream.of(
            Arguments.of(Set.of(PaymentMethodType.CARD, PaymentMethodType.CASH)), // 거래 방식을 추가하는 경우.
            Arguments.of(Collections.emptySet()), // 거래 방식을 없애는 경우
            Arguments.of(Set.of(PaymentMethodType.CARD)) // 유지하는 경우
        );
    }

    @MethodSource("source_update_appearance_day")
    @ParameterizedTest
    void 가게의_개시일을_수정한다(Set<DayOfTheWeek> appearanceDays) {
        // given
        Store store = StoreCreator.create(userId, "storeName");
        store.addAppearanceDays(Set.of(DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY));
        storeRepository.save(store);

        UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
            .latitude(34.0)
            .longitude(130.0)
            .storeName("붕어빵")
            .storeType(StoreType.STORE)
            .appearanceDays(appearanceDays)
            .paymentMethods(Collections.emptySet())
            .menu(Collections.emptyList())
            .build();

        // when
        storeService.updateStore(store.getId(), request, userId);

        // then
        List<AppearanceDay> appearanceDayList = appearanceDayRepository.findAll();
        assertThat(appearanceDayList).hasSize(appearanceDays.size());
        assertThat(getDayOfTheWeeks(appearanceDayList)).containsAll(appearanceDays);
    }

    private static Stream<Arguments> source_update_appearance_day() {
        return Stream.of(
            Arguments.of(Set.of(DayOfTheWeek.MONDAY, DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY)), // 거래 방식을 추가하는 경우.
            Arguments.of(Set.of(DayOfTheWeek.TUESDAY)), // 거래 방식을 없애는 경우
            Arguments.of(Set.of(DayOfTheWeek.WEDNESDAY, DayOfTheWeek.TUESDAY)) // 유지하는 경우
        );
    }

    @Test
    void 가게의_메뉴를_수정한다() {
        // given
        Store store = StoreCreator.create(userId, "storeName");
        store.addMenus(Collections.singletonList(Menu.of(store, "이름", "가격", MenuCategoryType.BUNGEOPPANG)));
        storeRepository.save(store);

        String menuName = "메뉴 이름";
        String price = "10000";
        MenuCategoryType type = MenuCategoryType.BUNGEOPPANG;
        List<MenuRequest> menus = Collections.singletonList(MenuRequest.of(menuName, price, type));

        UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
            .latitude(34.0)
            .longitude(130.0)
            .storeName("붕어빵")
            .storeType(StoreType.STORE)
            .appearanceDays(Collections.emptySet())
            .paymentMethods(Collections.emptySet())
            .menu(menus)
            .build();

        // when
        storeService.updateStore(store.getId(), request, userId);

        // then
        List<Menu> findMenus = menuRepository.findAll();
        assertThat(findMenus).hasSize(1);
        assertMenu(findMenus.get(0), menuName, price, type);
    }

    private List<DayOfTheWeek> getDayOfTheWeeks(List<AppearanceDay> appearanceDays) {
        return appearanceDays.stream()
            .map(AppearanceDay::getDay)
            .collect(Collectors.toList());
    }

    private List<PaymentMethodType> getPaymentMethodTypes(List<PaymentMethod> paymentMethods) {
        return paymentMethods.stream()
            .map(PaymentMethod::getMethod)
            .collect(Collectors.toList());
    }

    private void assertMenu(Menu menu, String menuName, String price, MenuCategoryType type) {
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getPrice()).isEqualTo(price);
        assertThat(menu.getCategory()).isEqualTo(type);
    }

    private void assertPaymentMethod(PaymentMethod paymentMethod, PaymentMethodType type, Long storeId) {
        assertThat(paymentMethod.getStore().getId()).isEqualTo(storeId);
        assertThat(paymentMethod.getMethod()).isEqualTo(type);
    }

    private void assertAppearanceDay(AppearanceDay appearanceDay, DayOfTheWeek day, Long storeId) {
        assertThat(appearanceDay.getStore().getId()).isEqualTo(storeId);
        assertThat(appearanceDay.getDay()).isEqualTo(day);
    }

    private void assertStore(Store store, Double latitude, Double longitude, String storeName, StoreType storeType, Long userId) {
        assertThat(store.getLocation()).isEqualTo(Location.of(latitude, longitude));
        assertThat(store.getLatitude()).isEqualTo(latitude);
        assertThat(store.getLongitude()).isEqualTo(longitude);
        assertThat(store.getName()).isEqualTo(storeName);
        assertThat(store.getType()).isEqualTo(storeType);
        assertThat(store.getUserId()).isEqualTo(userId);
    }

}
