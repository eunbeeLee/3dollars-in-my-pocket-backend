package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @AfterEach
    void cleanUp() {
        menuRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
    }

    @Test
    void 반경_3KM의_Store를_조회한다() {
        // given
        Store store = Store.builder()
            .userId(100L)
            .latitude(37.358483)
            .longitude(126.930947)
            .name("storeName")
            .type(StoreType.STORE)
            .build();
        store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
        storeRepository.save(store);

        // when
        List<Store> stores = storeRepository.findStoresByLocationLessThanDistance(37.358086, 126.933012, 2.0);

        // then
        assertThat(stores).hasSize(1);
    }

    @Test
    void 반경_3KM의_Store를_조회한다1() {
        // given
        Store store = Store.builder()
            .userId(100L)
            .latitude(37.328431)
            .longitude(126.91674)
            .name("storeName")
            .type(StoreType.STORE)
            .build();
        store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
        storeRepository.save(store);

        // when
        List<Store> stores = storeRepository.findStoresByLocationLessThanDistance(37.358086, 126.933012, 2.0);

        // then
        assertThat(stores).isEmpty();
    }

    @Test
    void 사용자가_등록한_가게들을_페이지네이션으로_조회한다() {
        // given
        Long userId = 100L;

        for (int i = 0; i < 30; i++) {
            Store store = StoreCreator.create(userId, String.format("%s번 가게", i + 1));
            store.addMenus(Collections.singletonList(MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));
            storeRepository.save(store);
        }

        // when
        Page<Store> stores = storeRepository.findAllByUserIdWithPagination(userId, PageRequest.of(1, 2));

        // then
        assertThat(stores.getTotalElements()).isEqualTo(30);
        assertThat(stores.getTotalPages()).isEqualTo(15);
        assertThat(stores.getContent()).hasSize(2);
        assertThat(stores.getContent().get(0).getName()).isEqualTo("28번 가게");
        assertThat(stores.getContent().get(1).getName()).isEqualTo("27번 가게");
    }

}
