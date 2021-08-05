package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.controller.AbstractControllerTest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveNearStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveMyStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreDetailInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.MenuResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoresScrollResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDetailResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import com.depromeet.threedollar.domain.domain.store.*;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StoreRetrieveControllerTest extends AbstractControllerTest {

    private StoreRetrieveMockApiCaller storeRetrieveMockApiCaller;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        storeRetrieveMockApiCaller = new StoreRetrieveMockApiCaller(mockMvc, objectMapper);
    }

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

    @DisplayName("GET /api/v2/stores/near")
    @Nested
    class 사용자_주변의_가게_조회 {

        @Test
        void 사용자_주변의_가게들을_조회한다() throws Exception {
            // given
            Store store1 = StoreCreator.create(testUser.getId(), "storeName", 34, 124);
            Menu menu1 = MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG);
            Menu menu2 = MenuCreator.create(store1, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG);
            Menu menu3 = MenuCreator.create(store1, "메뉴3", "가격3", MenuCategoryType.EOMUK);
            store1.addMenus(Arrays.asList(menu1, menu2, menu3));

            Store store2 = StoreCreator.create(testUser.getId(), "storeName", 34, 124);
            store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
            storeRepository.saveAll(Arrays.asList(store1, store2));

            RetrieveNearStoresRequest request = RetrieveNearStoresRequest.testInstance(34, 124, 34, 124, 1000);

            // when
            ApiResponse<List<StoreInfoResponse>> response = storeRetrieveMockApiCaller.getNearStores(request, 200);

            // then
            assertThat(response.getData()).hasSize(2);
            assertStoreInfoResponse(response.getData().get(0), store1.getId(), store1.getLatitude(), store1.getLongitude(), store1.getName(), store1.getRating());
            assertThat(response.getData().get(0).getCategories()).hasSize(2);
            assertThat(response.getData().get(0).getCategories().get(0)).isEqualTo(MenuCategoryType.BUNGEOPPANG);
            assertThat(response.getData().get(0).getCategories().get(1)).isEqualTo(MenuCategoryType.EOMUK);

            assertStoreInfoResponse(response.getData().get(1), store2.getId(), store2.getLatitude(), store2.getLongitude(), store2.getName(), store2.getRating());
            assertThat(response.getData().get(1).getCategories()).hasSize(1);
            assertThat(response.getData().get(1).getCategories().get(0)).isEqualTo(MenuCategoryType.BUNGEOPPANG);
        }

    }

    @DisplayName("GET /api/v2/store")
    @Nested
    class 특정_가게_상세_정보_조회 {

        @Test
        void 특정_가게에_대한_상세_정보를_조회한다() throws Exception {
            // given
            Store store = StoreCreator.create(testUser.getId(), "storeName", 34, 124);

            Menu menu = MenuCreator.create(store, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG);
            store.addMenus(Collections.singletonList(menu));

            DayOfTheWeek day = DayOfTheWeek.FRIDAY;
            store.addAppearanceDays(Collections.singleton(day));

            PaymentMethodType paymentMethodType = PaymentMethodType.CASH;
            store.addPaymentMethods(Collections.singleton(paymentMethodType));

            storeRepository.save(store);

            RetrieveStoreDetailInfoRequest request = RetrieveStoreDetailInfoRequest.testInstance(store.getId(), 34, 124);

            // when
            ApiResponse<StoreDetailResponse> response = storeRetrieveMockApiCaller.getStoreDetailInfo(request, 200);

            // then
            StoreDetailResponse data = response.getData();
            assertStoreDetailInfoResponse(data, store.getId(), store.getLatitude(), store.getLongitude(), store.getName(), store.getType(), store.getRating());

            assertThat(data.getCategories()).hasSize(1);
            assertThat(data.getCategories().get(0)).isEqualTo(menu.getCategory());

            assertThat(data.getMenus()).hasSize(1);
            assertMenuResponse(data.getMenus().get(0), menu.getId(), menu.getCategory(), menu.getName(), menu.getPrice());

            assertUserInfoResponse(data.getUser(), testUser.getId(), testUser.getName(), testUser.getSocialType());

            assertThat(data.getAppearanceDays()).hasSize(1);
            assertThat(data.getAppearanceDays().contains(day)).isTrue();

            assertThat(data.getPaymentMethods()).hasSize(1);
            assertThat(data.getPaymentMethods().contains(paymentMethodType)).isTrue();
        }

        @Test
        void 특정_가게에_대한_상세_정보를_조회할때_회원탈퇴한_유저의경우_사라진_제보자라고_표기된다() throws Exception {
            // given
            Store store = StoreCreator.create(999L, "storeName", 34, 124);
            store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
            storeRepository.save(store);

            RetrieveStoreDetailInfoRequest request = RetrieveStoreDetailInfoRequest.testInstance(store.getId(), 34, 124);

            // when
            ApiResponse<StoreDetailResponse> response = storeRetrieveMockApiCaller.getStoreDetailInfo(request, 200);

            // then
            StoreDetailResponse data = response.getData();
            assertUserInfoResponse(data.getUser(), null, "사라진 제보자", null);
        }

    }

    @DisplayName("GET /api/v2/stores/me")
    @Nested
    class 사용자가_작성한_가게들_조회 {

        @Test
        void 사용자가_작성한_가게조회_첫_페이지_조회시_다음_커서가_반환된다() throws Exception {
            // given
            Store store1 = StoreCreator.create(testUser.getId(), "가게1", 34, 124);
            store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)));

            Store store2 = StoreCreator.create(testUser.getId(), "가게2", 34, 124);
            store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)));

            Store store3 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)));

            Store store4 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store4.addMenus(Collections.singletonList(MenuCreator.create(store4, "메뉴4", "가격4", MenuCategoryType.BUNGEOPPANG)));

            storeRepository.saveAll(Arrays.asList(store1, store2, store3, store4));

            RetrieveMyStoresRequest request = RetrieveMyStoresRequest.testInstance(2, null, null, 34, 124);

            // when
            ApiResponse<StoresScrollResponse> response = storeRetrieveMockApiCaller.getMyStores(request, token, 200);

            // then
            assertThat(response.getData().getTotalElements()).isEqualTo(4);
            assertThat(response.getData().getNextCursor()).isEqualTo(store3.getId());
            assertThat(response.getData().getContents()).hasSize(2);
            assertStoreInfoResponse(response.getData().getContents().get(0), store4);
            assertStoreInfoResponse(response.getData().getContents().get(1), store3);
        }

        @Test
        void 사용자가_작성한_가게조회_중간_페이지_조회시_다음_커서가_반환된다() throws Exception {
            // given
            Store store1 = StoreCreator.create(testUser.getId(), "가게1", 34, 124);
            store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)));

            Store store2 = StoreCreator.create(testUser.getId(), "가게2", 34, 124);
            store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)));

            Store store3 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)));

            Store store4 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store4.addMenus(Collections.singletonList(MenuCreator.create(store4, "메뉴4", "가격4", MenuCategoryType.BUNGEOPPANG)));

            storeRepository.saveAll(Arrays.asList(store1, store2, store3, store4));

            RetrieveMyStoresRequest request = RetrieveMyStoresRequest.testInstance(2, store4.getId(), 4L, 34, 124);

            // when
            ApiResponse<StoresScrollResponse> response = storeRetrieveMockApiCaller.getMyStores(request, token, 200);

            // then
            assertThat(response.getData().getTotalElements()).isEqualTo(4);
            assertThat(response.getData().getNextCursor()).isEqualTo(store2.getId());
            assertThat(response.getData().getContents()).hasSize(2);
            assertStoreInfoResponse(response.getData().getContents().get(0), store3);
            assertStoreInfoResponse(response.getData().getContents().get(1), store2);
        }

        @Test
        void 사용자가_작성한_가게조회_중간_페이지_조회시_다음_커서가_반환된다_총개수가_캐싱되지_않으면_계산되서_반환() throws Exception {
            // given
            Store store1 = StoreCreator.create(testUser.getId(), "가게1", 34, 124);
            store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)));

            Store store2 = StoreCreator.create(testUser.getId(), "가게2", 34, 124);
            store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)));

            Store store3 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)));

            Store store4 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store4.addMenus(Collections.singletonList(MenuCreator.create(store4, "메뉴4", "가격4", MenuCategoryType.BUNGEOPPANG)));

            storeRepository.saveAll(Arrays.asList(store1, store2, store3, store4));

            RetrieveMyStoresRequest request = RetrieveMyStoresRequest.testInstance(2, store4.getId(), null, 34, 124);

            // when
            ApiResponse<StoresScrollResponse> response = storeRetrieveMockApiCaller.getMyStores(request, token, 200);

            // then
            assertThat(response.getData().getTotalElements()).isEqualTo(4);
            assertThat(response.getData().getNextCursor()).isEqualTo(store2.getId());
            assertThat(response.getData().getContents()).hasSize(2);
            assertStoreInfoResponse(response.getData().getContents().get(0), store3);
            assertStoreInfoResponse(response.getData().getContents().get(1), store2);
        }

        @Test
        void 사용자가_작성한_가게조회_contents가_size_와_동일하면_다음_스크롤에_해당하는_가게들을_조회하고_없으면_마지막_페이지로_판단하고_커서가_null을_반환한다() throws Exception {
            // given
            Store store1 = StoreCreator.create(testUser.getId(), "가게1", 34, 124);
            store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)));

            Store store2 = StoreCreator.create(testUser.getId(), "가게2", 34, 124);
            store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)));

            Store store3 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)));

            Store store4 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store4.addMenus(Collections.singletonList(MenuCreator.create(store4, "메뉴4", "가격4", MenuCategoryType.BUNGEOPPANG)));

            storeRepository.saveAll(Arrays.asList(store1, store2, store3, store4));

            RetrieveMyStoresRequest request = RetrieveMyStoresRequest.testInstance(2, store3.getId(), null, 34, 124);

            // when
            ApiResponse<StoresScrollResponse> response = storeRetrieveMockApiCaller.getMyStores(request, token, 200);

            // then
            assertThat(response.getData().getTotalElements()).isEqualTo(4);
            assertThat(response.getData().getNextCursor()).isNull();
            assertThat(response.getData().getContents()).hasSize(2);
            assertStoreInfoResponse(response.getData().getContents().get(0), store2);
            assertStoreInfoResponse(response.getData().getContents().get(1), store1);
        }

        @Test
        void 사용자가_작성한_contents가_size보다_적으면_마지막_페이지로_판단하고_Cursor가_null로_반환된다() throws Exception {
            // given
            Store store1 = StoreCreator.create(testUser.getId(), "가게1", 34, 124);
            store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)));

            Store store2 = StoreCreator.create(testUser.getId(), "가게2", 34, 124);
            store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)));

            Store store3 = StoreCreator.create(testUser.getId(), "가게3", 34, 124);
            store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)));

            storeRepository.saveAll(Arrays.asList(store1, store2, store3));

            RetrieveMyStoresRequest request = RetrieveMyStoresRequest.testInstance(2, store2.getId(), 3L, 34, 124);

            // when
            ApiResponse<StoresScrollResponse> response = storeRetrieveMockApiCaller.getMyStores(request, token, 200);

            // then
            assertThat(response.getData().getTotalElements()).isEqualTo(3);
            assertThat(response.getData().getNextCursor()).isNull();
            assertThat(response.getData().getContents()).hasSize(1);
            assertStoreInfoResponse(response.getData().getContents().get(0), store1);
        }

    }

    private void assertStoreInfoResponse(StoreInfoResponse response, Store store) {
        assertThat(response.getStoreId()).isEqualTo(store.getId());
        assertThat(response.getStoreName()).isEqualTo(store.getName());
        assertThat(response.getLatitude()).isEqualTo(store.getLatitude());
        assertThat(response.getLongitude()).isEqualTo(store.getLongitude());
        assertThat(response.getRating()).isEqualTo(store.getRating());
    }

    private void assertUserInfoResponse(UserInfoResponse user, Long userId, String name, UserSocialType socialType) {
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSocialType()).isEqualTo(socialType);
    }

    private void assertMenuResponse(MenuResponse respose, Long menuId, MenuCategoryType category, String name, String price) {
        assertThat(respose.getMenuId()).isEqualTo(menuId);
        assertThat(respose.getCategory()).isEqualTo(category);
        assertThat(respose.getName()).isEqualTo(name);
        assertThat(respose.getPrice()).isEqualTo(price);
    }

    private void assertStoreDetailInfoResponse(StoreDetailResponse response, Long storeId, Double latitude, Double longitude, String name, StoreType type, double rating) {
        assertThat(response.getStoreId()).isEqualTo(storeId);
        assertThat(response.getLatitude()).isEqualTo(latitude);
        assertThat(response.getLongitude()).isEqualTo(longitude);
        assertThat(response.getStoreName()).isEqualTo(name);
        assertThat(response.getStoreType()).isEqualTo(type);
        assertThat(response.getRating()).isEqualTo(rating);
    }

    private void assertStoreInfoResponse(StoreInfoResponse response, Long id, Double latitude, Double longitude, String name, double rating) {
        assertThat(response.getStoreId()).isEqualTo(id);
        assertThat(response.getLatitude()).isEqualTo(latitude);
        assertThat(response.getLongitude()).isEqualTo(longitude);
        assertThat(response.getStoreName()).isEqualTo(name);
        assertThat(response.getRating()).isEqualTo(rating);
    }

}
