package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.MenuRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.common.Location;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import com.depromeet.threedollar.domain.domain.store.*;
import com.depromeet.threedollar.common.exception.notfound.NotFoundStoreException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Nested
    class 가게_정보_등록 {

        @Test
        void 성공시_새로운_가게_데이터가_DB에_추가된다() {
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
                .menus(menu)
                .build();

            // when
            storeService.addStore(request, userId);

            // then
            List<Store> stores = storeRepository.findAll();
            assertThat(stores).hasSize(1);
            assertStore(stores.get(0), latitude, longitude, storeName, storeType, userId);
        }

        @Test
        void 성공시_게시일_테이블에_새로운_게시일_정보도_추가된다() {
            // given
            Set<DayOfTheWeek> appearanceDays = Set.of(DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY);

            AddStoreRequest request = AddStoreRequest.testBuilder()
                .latitude(34.0)
                .longitude(130.0)
                .storeName("붕어빵")
                .storeType(StoreType.STORE)
                .appearanceDays(appearanceDays)
                .paymentMethods(Collections.emptySet())
                .menus(Collections.emptyList())
                .build();

            // when
            storeService.addStore(request, userId);

            // then
            List<AppearanceDay> appearanceDayList = appearanceDayRepository.findAll();
            assertThat(appearanceDayList).hasSize(2);
            assertThat(getDayOfTheWeeks(appearanceDayList)).containsAll(Arrays.asList(DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY));
        }

        @Test
        void 성공시_결제방법_테이블에_결제_방법도_추가된다() {
            // given
            Set<PaymentMethodType> paymentMethods = Set.of(PaymentMethodType.CARD, PaymentMethodType.CASH);

            AddStoreRequest request = AddStoreRequest.testBuilder()
                .latitude(34.0)
                .longitude(130.0)
                .storeName("붕어빵")
                .storeType(StoreType.STORE)
                .appearanceDays(Collections.emptySet())
                .paymentMethods(paymentMethods)
                .menus(Collections.emptyList())
                .build();

            // when
            storeService.addStore(request, userId);

            // then
            List<PaymentMethod> paymentMethodsList = paymentMethodRepository.findAll();
            assertThat(paymentMethodsList).hasSize(2);
            assertThat(getPaymentMethodTypes(paymentMethodsList)).containsAll(Arrays.asList(PaymentMethodType.CARD, PaymentMethodType.CASH));
        }

        @Test
        void 성공시_메뉴_테이블에_메뉴들도_함께_추가된다() {
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
                .menus(menus)
                .build();

            // when
            storeService.addStore(request, userId);

            // then
            List<Menu> menuList = menuRepository.findAll();
            assertThat(menuList).hasSize(1);
            assertMenu(menuList.get(0), menuName, price, type);
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 가게_정보_수정 {

        @Test
        void 성공시_기존_가게정보_데이터들이_수정된다() {
            // given
            Store store = StoreCreator.create(userId, "storeName");
            store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
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
                .menus(menu)
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

        @Test
        void 사용자가_작성하지_않은_가게_정보도_수정할수있다() {
            // given
            Store store = StoreCreator.create(100L, "storeName");
            store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
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
                .menus(menu)
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

        @MethodSource
        @ParameterizedTest
        void 가게의_결제방법을_수정한다(Set<PaymentMethodType> paymentMethodTypes) {
            // given
            Store store = StoreCreator.create(userId, "storeName");
            store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
            store.addPaymentMethods(Set.of(PaymentMethodType.CARD));
            storeRepository.save(store);

            UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
                .latitude(34.0)
                .longitude(130.0)
                .storeName("붕어빵")
                .storeType(StoreType.STORE)
                .appearanceDays(Collections.emptySet())
                .paymentMethods(paymentMethodTypes)
                .menus(Collections.emptyList())
                .build();

            // when
            storeService.updateStore(store.getId(), request, userId);

            // then
            List<PaymentMethod> paymentMethodsList = paymentMethodRepository.findAll();
            assertThat(paymentMethodsList).hasSize(paymentMethodTypes.size());
            assertThat(getPaymentMethodTypes(paymentMethodsList)).containsAll(paymentMethodTypes);
        }

        private Stream<Arguments> 가게의_결제방법을_수정한다() {
            return Stream.of(
                Arguments.of(Set.of(PaymentMethodType.CARD, PaymentMethodType.CASH)), // 거래 방식을 추가하는 경우.
                Arguments.of(Collections.emptySet()), // 거래 방식을 없애는 경우
                Arguments.of(Set.of(PaymentMethodType.CARD)) // 유지하는 경우
            );
        }

        @MethodSource
        @ParameterizedTest
        void 가게의_개시일을_수정한다(Set<DayOfTheWeek> appearanceDays) {
            // given
            Store store = StoreCreator.create(userId, "storeName");
            store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
            store.addAppearanceDays(Set.of(DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY));
            storeRepository.save(store);

            UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
                .latitude(34.0)
                .longitude(130.0)
                .storeName("붕어빵")
                .storeType(StoreType.STORE)
                .appearanceDays(appearanceDays)
                .paymentMethods(Collections.emptySet())
                .menus(Collections.emptyList())
                .build();

            // when
            storeService.updateStore(store.getId(), request, userId);

            // then
            List<AppearanceDay> appearanceDayList = appearanceDayRepository.findAll();
            assertThat(appearanceDayList).hasSize(appearanceDays.size());
            assertThat(getDayOfTheWeeks(appearanceDayList)).containsAll(appearanceDays);
        }

        private Stream<Arguments> 가게의_개시일을_수정한다() {
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
                .menus(menus)
                .build();

            // when
            storeService.updateStore(store.getId(), request, userId);

            // then
            List<Menu> findMenus = menuRepository.findAll();
            assertThat(findMenus).hasSize(1);
            assertMenu(findMenus.get(0), store.getId(), menuName, price, type);
        }

        @Test
        void 해당하는_가게가_존재하지_않으면_NOT_FOUND_STORE_EXCEPTION() {
            // given
            UpdateStoreRequest request = UpdateStoreRequest.testBuilder()
                .latitude(34.0)
                .longitude(130.0)
                .storeName("붕어빵")
                .storeType(StoreType.STORE)
                .appearanceDays(Set.of(DayOfTheWeek.TUESDAY))
                .paymentMethods(Set.of(PaymentMethodType.CARD))
                .menus(Collections.singletonList(MenuRequest.of("메뉴 이름", "메뉴 가격", MenuCategoryType.BUNGEOPPANG)))
                .build();

            // when & then
            assertThatThrownBy(() -> storeService.updateStore(999L, request, userId)).isInstanceOf(NotFoundStoreException.class);
        }

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

    private void assertMenu(Menu menu, Long storeId, String menuName, String price, MenuCategoryType type) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
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
