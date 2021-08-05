package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.controller.AbstractControllerTest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.MenuRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDeleteResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import com.depromeet.threedollar.domain.domain.store.*;
import com.depromeet.threedollar.domain.domain.storedelete.DeleteReasonType;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestCreator;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.depromeet.threedollar.common.exception.ErrorCode.NOT_FOUND_STORE_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;

class StoreControllerTest extends AbstractControllerTest {

    private StoreMockApiCaller storeMockApiCaller;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        storeMockApiCaller = new StoreMockApiCaller(mockMvc, objectMapper);
    }

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AppearanceDayRepository appearanceDayRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private StoreDeleteRequestRepository storeDeleteRequestRepository;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        appearanceDayRepository.deleteAllInBatch();
        paymentMethodRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
        storeDeleteRequestRepository.deleteAll();
    }

    @DisplayName("POST /api/v2/store 200 OK")
    @Test
    void 새로운_가게정보를_등록하는_API_호출시_200_OK() throws Exception {
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
        ApiResponse<StoreInfoResponse> response = storeMockApiCaller.addStore(request, token, 200);

        // then
        assertStoreInfoResponse(response.getData(), latitude, longitude, storeName, Collections.singletonList(MenuCategoryType.BUNGEOPPANG));
    }

    @DisplayName("PUT /api/v2/store 200 OK")
    @Test
    void 특정_가게정보를_수정하는_API_호출시_200_OK() throws Exception {
        // given
        Store store = StoreCreator.create(testUser.getId(), "storeName");
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
        ApiResponse<StoreInfoResponse> response = storeMockApiCaller.updateStore(store.getId(), request, token, 200);

        // then
        assertThat(response.getData().getStoreId()).isEqualTo(store.getId());
        assertStoreInfoResponse(response.getData(), latitude, longitude, storeName, Collections.singletonList(MenuCategoryType.BUNGEOPPANG));
    }

    @DisplayName("DELETE /api/v2/store 200 OK 삭제 요청만 쌓이는 경우")
    @Test
    void 특정_가게정보를_삭제하는_API_호출시_200_OK() throws Exception {
        // given
        Store store = StoreCreator.create(testUser.getId(), "storeName");
        storeRepository.save(store);

        DeleteStoreRequest request = DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE);

        // when
        ApiResponse<StoreDeleteResponse> response = storeMockApiCaller.deleteStore(store.getId(), request, token, 200);

        // then
        assertThat(response.getData().getIsDeleted()).isFalse();
    }

    @DisplayName("DELETE /api/v2/store 200 OK 실제 삭제되는 경우")
    @Test
    void 특정_가게정보를_삭제하는_API_호출시_3개가_되면_실제로_삭제되고_true_를_반환한다_200OK() throws Exception {
        // given
        Store store = StoreCreator.create(testUser.getId(), "storeName");
        storeRepository.save(store);

        storeDeleteRequestRepository.saveAll(Arrays.asList(
            StoreDeleteRequestCreator.create(store.getId(), 10L, DeleteReasonType.NOSTORE),
            StoreDeleteRequestCreator.create(store.getId(), 11L, DeleteReasonType.NOSTORE)
        ));

        DeleteStoreRequest request = DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE);

        // when
        ApiResponse<StoreDeleteResponse> response = storeMockApiCaller.deleteStore(store.getId(), request, token, 200);

        // then
        assertThat(response.getData().getIsDeleted()).isTrue();
    }

    @DisplayName("DELETE /api/v2/store 존재하지 않는 가게인경우 404 Error")
    @Test
    void 존재하지_않는_특정_가게정보를_삭제하는_API_호출시_404_NOT_FOUND() throws Exception {
        // given
        DeleteStoreRequest request = DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE);

        // when
        ApiResponse<StoreDeleteResponse> response = storeMockApiCaller.deleteStore(999L, request, token, 404);

        // then
        assertThat(response.getResultCode()).isEqualTo(NOT_FOUND_STORE_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(NOT_FOUND_STORE_EXCEPTION.getMessage());
        assertThat(response.getData()).isNull();
    }

    private void assertStoreInfoResponse(StoreInfoResponse response, Double latitude, Double longitude, String storeName, List<MenuCategoryType> categories) {
        assertThat(response.getLatitude()).isEqualTo(latitude);
        assertThat(response.getLongitude()).isEqualTo(longitude);
        assertThat(response.getStoreName()).isEqualTo(storeName);
        assertThat(response.getCategories()).isEqualTo(categories);
    }

}
