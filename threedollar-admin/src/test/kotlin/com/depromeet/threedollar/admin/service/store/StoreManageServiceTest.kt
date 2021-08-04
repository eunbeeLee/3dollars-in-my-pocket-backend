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
internal class StoreManageServiceTest(
    @Autowired
    private val storeManageService: StoreManageService,

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
        val store = StoreCreator.create(100L, "가게 1")
        storeRepository.save(store)
        storeDeleteRequestRepository.saveAll(
            listOf(
                StoreDeleteRequestCreator.create(store.id, 100L, DeleteReasonType.NOSTORE),
                StoreDeleteRequestCreator.create(store.id, 101L, DeleteReasonType.NOSTORE)
            )
        )

        val request = RetrieveReportedStoresRequest(2)

        // when
        val result = storeManageService.retrieveReportedStores(request)

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].storeId).isEqualTo(store.id)
        assertThat(result[0].storeName).isEqualTo(store.name)
        assertThat(result[0].latitude).isEqualTo(store.latitude)
        assertThat(result[0].longitude).isEqualTo(store.longitude)
        assertThat(result[0].type).isEqualTo(store.type)
        assertThat(result[0].rating).isEqualTo(store.rating)
        assertThat(result[0].reportsCount).isEqualTo(2)
    }

}
