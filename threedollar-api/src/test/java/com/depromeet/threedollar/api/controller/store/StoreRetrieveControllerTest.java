package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.controller.AbstractControllerTest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveMyStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreDetailInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.MenuResponse;
import com.depromeet.threedollar.api.service.store.dto.response.MyStoresWithPaginationResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDetailInfoResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import com.depromeet.threedollar.domain.domain.store.*;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("GET /api/v2/stores/near 200 OK")
    @Test
    void 사용자_주위의_가게들을_조회한다() throws Exception {
        // given
        Store store1 = StoreCreator.create(testUser.getId(), "storeName", 34, 124);
        Menu menu1 = MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG);
        Menu menu2 = MenuCreator.create(store1, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG);
        Menu menu3 = MenuCreator.create(store1, "메뉴3", "가격3", MenuCategoryType.EOMUK);
        store1.addMenus(Arrays.asList(menu1, menu2, menu3));

        Store store2 = StoreCreator.create(testUser.getId(), "storeName", 34, 124);
        storeRepository.saveAll(Arrays.asList(store1, store2));

        RetrieveAroundStoresRequest request = RetrieveAroundStoresRequest.testInstance(34, 124, 34, 124, 1000);

        // when
        ApiResponse<List<StoreInfoResponse>> response = storeRetrieveMockApiCaller.getNearStores(request, 200);

        // then
        assertThat(response.getData()).hasSize(2);
        assertStoreInfoResponse(response.getData().get(0), store1.getId(), store1.getLatitude(), store1.getLongitude(), store1.getName(), store1.getRating(), MenuCategoryType.BUNGEOPPANG);
        assertThat(response.getData().get(0).getCategories()).hasSize(2);
        assertThat(response.getData().get(0).getCategories().get(0)).isEqualTo(MenuCategoryType.BUNGEOPPANG);
        assertThat(response.getData().get(0).getCategories().get(1)).isEqualTo(MenuCategoryType.EOMUK);

        assertStoreInfoResponse(response.getData().get(1), store2.getId(), store2.getLatitude(), store2.getLongitude(), store2.getName(), store2.getRating(), null);
        assertThat(response.getData().get(1).getCategories()).isEmpty();
    }

    @DisplayName("GET /api/v2/store 200 OK")
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
        ApiResponse<StoreDetailInfoResponse> response = storeRetrieveMockApiCaller.getStoreDetailInfo(request, 200);

        // then
        StoreDetailInfoResponse data = response.getData();
        assertStoreDetailInfoResponse(data, store.getId(), store.getLatitude(), store.getLongitude(), store.getName(), store.getType(), store.getRating(), menu.getCategory());

        assertThat(data.getCategories()).hasSize(1);
        assertThat(data.getCategories().get(0)).isEqualTo(menu.getCategory());

        assertThat(data.getMenu()).hasSize(1);
        assertMenuResponse(data.getMenu().get(0), menu.getId(), menu.getCategory(), menu.getName(), menu.getPrice());

        assertUserInfoResponse(data.getUser(), testUser.getId(), testUser.getName(), testUser.getSocialType());

        assertThat(data.getAppearanceDays()).hasSize(1);
        assertThat(data.getAppearanceDays().contains(day)).isTrue();

        assertThat(data.getPaymentMethods()).hasSize(1);
        assertThat(data.getPaymentMethods().contains(paymentMethodType)).isTrue();
    }

    @DisplayName("GET /api/v2/store 200 OK")
    @Test
    void 특정_가게에_대한_상세_정보를_조회할때_회원탈퇴한_유저의경우_사라진_제보자라고_보인다() throws Exception {
        // given
        Store store = StoreCreator.create(999L, "storeName", 34, 124);
        storeRepository.save(store);

        RetrieveStoreDetailInfoRequest request = RetrieveStoreDetailInfoRequest.testInstance(store.getId(), 34, 124);

        // when
        ApiResponse<StoreDetailInfoResponse> response = storeRetrieveMockApiCaller.getStoreDetailInfo(request, 200);

        // then
        StoreDetailInfoResponse data = response.getData();
        assertUserInfoResponse(data.getUser(), null, "사라진 제보자", null);
    }

    @DisplayName("GET /api/v2/stores/me 200 OK")
    @Test
    void 사용자가_작성한_가게들의_정보를_조회합니다() throws Exception {
        // given
        Store store1 = StoreCreator.create(testUser.getId(), "storeName", 34, 124);
        Menu menu1 = MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG);
        Menu menu2 = MenuCreator.create(store1, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG);
        Menu menu3 = MenuCreator.create(store1, "메뉴3", "가격3", MenuCategoryType.EOMUK);
        store1.addMenus(Arrays.asList(menu1, menu2, menu3));

        Store store2 = StoreCreator.create(testUser.getId(), "storeName", 34, 124);
        storeRepository.saveAll(Arrays.asList(store1, store2));

        RetrieveMyStoresRequest request = RetrieveMyStoresRequest.testInstance(2, 0, 34, 124);

        // when
        ApiResponse<MyStoresWithPaginationResponse> response = storeRetrieveMockApiCaller.getMyStores(request, token, 200);

        // then
        assertThat(response.getData().getTotalElements()).isEqualTo(2);
        assertThat(response.getData().getTotalPages()).isEqualTo(1);
        assertThat(response.getData().getContent()).hasSize(2);
        assertStoreInfoResponse(response.getData().getContent().get(0), store2.getId(), store2.getLatitude(), store2.getLongitude(), store2.getName(), store2.getRating(), null);
        assertThat(response.getData().getContent().get(0).getCategories()).isEmpty();

        assertStoreInfoResponse(response.getData().getContent().get(1), store1.getId(), store1.getLatitude(), store1.getLongitude(), store1.getName(), store1.getRating(), MenuCategoryType.BUNGEOPPANG);
        assertThat(response.getData().getContent().get(1).getCategories()).hasSize(2);
        assertThat(response.getData().getContent().get(1).getCategories().get(0)).isEqualTo(MenuCategoryType.BUNGEOPPANG);
        assertThat(response.getData().getContent().get(1).getCategories().get(1)).isEqualTo(MenuCategoryType.EOMUK);
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

    private void assertStoreDetailInfoResponse(StoreDetailInfoResponse response, Long storeId, Double latitude, Double longitude, String name, StoreType type, double rating, MenuCategoryType category) {
        assertThat(response.getStoreId()).isEqualTo(storeId);
        assertThat(response.getLatitude()).isEqualTo(latitude);
        assertThat(response.getLongitude()).isEqualTo(longitude);
        assertThat(response.getStoreName()).isEqualTo(name);
        assertThat(response.getStoreType()).isEqualTo(type);
        assertThat(response.getRating()).isEqualTo(rating);
        assertThat(response.getCategory()).isEqualTo(category);
    }

    private void assertStoreInfoResponse(StoreInfoResponse response, Long id, Double latitude, Double longitude, String name, double rating, MenuCategoryType category) {
        assertThat(response.getStoreId()).isEqualTo(id);
        assertThat(response.getLatitude()).isEqualTo(latitude);
        assertThat(response.getLongitude()).isEqualTo(longitude);
        assertThat(response.getStoreName()).isEqualTo(name);
        assertThat(response.getRating()).isEqualTo(rating);
        assertThat(response.getCategory()).isEqualTo(category);
    }

}
