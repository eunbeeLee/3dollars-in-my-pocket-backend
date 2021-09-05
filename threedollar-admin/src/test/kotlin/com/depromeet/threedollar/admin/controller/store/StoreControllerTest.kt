package com.depromeet.threedollar.admin.controller.store

import com.depromeet.threedollar.admin.controller.ControllerTestUtils
import com.depromeet.threedollar.admin.service.store.dto.response.StoreInfoResponse
import com.depromeet.threedollar.admin.service.store.dto.response.StoreScrollResponse
import com.depromeet.threedollar.application.common.dto.ApiResponse
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType
import com.depromeet.threedollar.domain.domain.menu.MenuCreator
import com.depromeet.threedollar.domain.domain.store.Store
import com.depromeet.threedollar.domain.domain.store.StoreCreator
import com.depromeet.threedollar.domain.domain.store.StoreRepository
import com.fasterxml.jackson.core.type.TypeReference
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class StoreControllerTest(
    @Autowired
    private val storeRepository: StoreRepository
) : ControllerTestUtils() {

    @BeforeEach
    fun setUp() {
        super.setup()
    }

    @AfterEach
    fun cleanUp() {
        super.cleanup()
        storeRepository.deleteAll()
    }

    @DisplayName("GET /admin/v1/store/latest")
    @Test
    fun 최신순으로_스크롤_페이지네이션으로_가게를_조회한다_첫스크롤() {
        // given
        val store1 = StoreCreator.create(100L, "가게1", 34.0, 124.0)
        store1.addMenus(listOf(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)))

        val store2 = StoreCreator.create(101L, "가게2", 34.0, 124.0)
        store2.addMenus(listOf(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)))

        val store3 = StoreCreator.create(102L, "가게3", 34.0, 124.0)
        store3.addMenus(listOf(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)))

        val store4 = StoreCreator.create(103L, "가게3", 34.0, 124.0)
        store4.addMenus(listOf(MenuCreator.create(store4, "메뉴4", "가격4", MenuCategoryType.BUNGEOPPANG)))

        storeRepository.saveAll(listOf(store1, store2, store3, store4))

        val size = 2
        val cursor = null

        // when
        val response = objectMapper.readValue(mockMvc.perform(
            get("/admin/v1/stores/latest")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .param("size", size.toString())
                .param("cursor", cursor)
        )
            .andExpect(status().isOk)
            .andDo(print())
            .andReturn()
            .response
            .contentAsString, object : TypeReference<ApiResponse<StoreScrollResponse>>() {}
        )

        // then
        assertThat(response.data.contents).hasSize(2)
        assertStoreInfoResponse(response.data.contents[0], store4)
        assertStoreInfoResponse(response.data.contents[1], store3)
        assertThat(response.data.nextCursor).isEqualTo(store3.id)
    }

    @DisplayName("GET /admin/v1/store/latest")
    @Test
    fun 최신순으로_스크롤_페이지네이션으로_가게를_조회한다_마지막_스크롤() {
        // given
        val store1 = StoreCreator.create(100L, "가게1", 34.0, 124.0)
        store1.addMenus(listOf(MenuCreator.create(store1, "메뉴1", "가격1", MenuCategoryType.BUNGEOPPANG)))

        val store2 = StoreCreator.create(101L, "가게2", 34.0, 124.0)
        store2.addMenus(listOf(MenuCreator.create(store2, "메뉴2", "가격2", MenuCategoryType.BUNGEOPPANG)))

        val store3 = StoreCreator.create(102L, "가게3", 34.0, 124.0)
        store3.addMenus(listOf(MenuCreator.create(store3, "메뉴3", "가격3", MenuCategoryType.BUNGEOPPANG)))

        val store4 = StoreCreator.create(103L, "가게3", 34.0, 124.0)
        store4.addMenus(listOf(MenuCreator.create(store4, "메뉴4", "가격4", MenuCategoryType.BUNGEOPPANG)))

        storeRepository.saveAll(listOf(store1, store2, store3, store4))

        val size = 2
        val cursor = store3.id

        // when
        val response = objectMapper.readValue(mockMvc.perform(
            get("/admin/v1/stores/latest")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .param("size", size.toString())
                .param("cursor", cursor.toString())
        )
            .andExpect(status().isOk)
            .andDo(print())
            .andReturn()
            .response
            .contentAsString, object : TypeReference<ApiResponse<StoreScrollResponse>>() {}
        )

        // then
        assertThat(response.data.contents).hasSize(2)
        assertStoreInfoResponse(response.data.contents[0], store2)
        assertStoreInfoResponse(response.data.contents[1], store1)
        assertThat(response.data.nextCursor).isEqualTo(-1)
    }

    private fun assertStoreInfoResponse(storeInfoResponse: StoreInfoResponse, store: Store) {
        assertThat(storeInfoResponse.storeId).isEqualTo(store.id)
        assertThat(storeInfoResponse.storeName).isEqualTo(store.name)
        assertThat(storeInfoResponse.categories).isEqualTo(store.menuCategories)
        assertThat(storeInfoResponse.latitude).isEqualTo(store.latitude)
        assertThat(storeInfoResponse.longitude).isEqualTo(store.longitude)
        assertThat(storeInfoResponse.rating).isEqualTo(store.rating)
    }

}
