package com.depromeet.threedollar.admin.service.store

import com.depromeet.threedollar.admin.service.AdminSetUpTest
import com.depromeet.threedollar.admin.service.store.dto.request.RetrieveReportedStoresRequest
import com.depromeet.threedollar.domain.domain.store.StoreCreator
import com.depromeet.threedollar.domain.domain.store.StoreRepository
import com.depromeet.threedollar.domain.domain.storedelete.DeleteReasonType
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestCreator
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class StoreAdminServiceTest(
    @Autowired
    private val storeAdminService: StoreAdminService,

    @Autowired
    private val storeRepository: StoreRepository,

    @Autowired
    private val storeDeleteRequestRepository: StoreDeleteRequestRepository
) : AdminSetUpTest() {

    @AfterEach
    fun cleanUp() {
        super.cleanup()
        storeRepository.deleteAll()
        storeDeleteRequestRepository.deleteAll()
    }

    @Test
    fun 가게삭제요청이_N개_이상_들어온_가게들을_조회한다() {
        // given
        val store1 = StoreCreator.create(100L, "가게 1")
        val store2 = StoreCreator.create(100L, "가게 2")
        val store3 = StoreCreator.create(100L, "가게 2")
        storeRepository.saveAll(listOf(store1, store2, store3))
        storeDeleteRequestRepository.saveAll(
            listOf(
                StoreDeleteRequestCreator.create(store1.id, 100L, DeleteReasonType.NOSTORE),
                StoreDeleteRequestCreator.create(store1.id, 101L, DeleteReasonType.OVERLAPSTORE),
                StoreDeleteRequestCreator.create(store2.id, 100L, DeleteReasonType.WRONGNOPOSITION),
            )
        )

        val request = RetrieveReportedStoresRequest(2, 1, 3)

        // when
        val result = storeAdminService.retrieveReportedStores(request)

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].storeId).isEqualTo(store1.id)
        assertThat(result[0].storeName).isEqualTo(store1.name)
        assertThat(result[0].latitude).isEqualTo(store1.latitude)
        assertThat(result[0].longitude).isEqualTo(store1.longitude)
        assertThat(result[0].type).isEqualTo(store1.type)
        assertThat(result[0].rating).isEqualTo(store1.rating)
        assertThat(result[0].reportsCount).isEqualTo(2)
    }

}
