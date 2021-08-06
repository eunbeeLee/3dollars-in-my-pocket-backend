package com.depromeet.threedollar.domain.domain.storedelete;

import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreCreator;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import com.depromeet.threedollar.domain.domain.storedelete.projection.ReportedStoreProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StoreDeleteRequestRepositoryTest {

    @Autowired
    private StoreDeleteRequestRepository storeDeleteRequestRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void N개_이상_삭제요청된_가게들을_조회한다() {
        // given
        Store store1 = StoreCreator.create(100L, "123");
        Store store2 = StoreCreator.create(100L, "123");
        storeRepository.saveAll(Arrays.asList(store1, store2));

        storeDeleteRequestRepository.saveAll(Arrays.asList(
            StoreDeleteRequestCreator.create(store1.getId(), 100L, DeleteReasonType.NOSTORE),
            StoreDeleteRequestCreator.create(store1.getId(), 100L, DeleteReasonType.NOSTORE),
            StoreDeleteRequestCreator.create(store2.getId(), 100L, DeleteReasonType.NOSTORE)
        ));

        // when
        List<ReportedStoreProjection> stores = storeDeleteRequestRepository.findStoreHasDeleteRequestMoreThanCnt(1);

        // then
        assertThat(stores).hasSize(2);
        assertStoreDeleteRequestReportDto(stores.get(0), store1.getId(), store1.getName(), store1.getLatitude(), store1.getLongitude(), store1.getType(), store1.getRating(), 2);
        assertStoreDeleteRequestReportDto(stores.get(1), store2.getId(), store2.getName(), store2.getLatitude(), store2.getLongitude(), store2.getType(), store2.getRating(), 1);
    }

    private void assertStoreDeleteRequestReportDto(ReportedStoreProjection response, Long storeId, String name, Double latitude, Double longitude, StoreType type, double rating, int cnt) {
        assertThat(response.getStoreId()).isEqualTo(storeId);
        assertThat(response.getStoreName()).isEqualTo(name);
        assertThat(response.getLatitude()).isEqualTo(latitude);
        assertThat(response.getLongitude()).isEqualTo(longitude);
        assertThat(response.getType()).isEqualTo(type);
        assertThat(response.getRating()).isEqualTo(rating);
        assertThat(response.getReportsCount()).isEqualTo(cnt);
    }

}
