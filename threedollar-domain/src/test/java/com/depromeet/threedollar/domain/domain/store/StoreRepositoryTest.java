package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void 반경_3KM의_가게들을_조회한다() {
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
    void 반경_3KM의_가게들을_조회할떄_아무_가게가_없는경우_빈_리스트를_반환한다() {
        // given
        Store store = Store.builder()
            .userId(100L)
            .latitude(37.328431)
            .longitude(126.91674)
            .name("storeName")
            .type(StoreType.STORE)
            .build();

        Menu menu = MenuCreator.create(store, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG);
        store.addMenus(Collections.singletonList(menu));
        storeRepository.save(store);

        // when
        List<Store> stores = storeRepository.findStoresByLocationLessThanDistance(37.358086, 126.933012, 2.0);

        // then
        assertThat(stores).isEmpty();
    }

    @Test
    void 사용자가_등록한_가게들의_총_개수를_조회한다() {
        // given
        Long userId = 100L;

        Store store1 = StoreCreator.create(userId, "1번 가게");
        store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "붕어빵1", "만원1", MenuCategoryType.BUNGEOPPANG)));
        store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "붕어빵2", "만원2", MenuCategoryType.BUNGEOPPANG)));

        Store store2 = StoreCreator.create(userId, "2번 가게");
        store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        Store store3 = StoreCreator.create(userId, "3번 가게");
        store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        storeRepository.saveAll(Arrays.asList(store1, store2, store3));

        // when
        long count = storeRepository.findCountsByUserId(userId);

        // then
        assertThat(count).isEqualTo(3);
    }

    @Test
    void 사용자가_등록한_가게_첫페이지_조회() {
        // given
        Long userId = 100L;

        Store store1 = StoreCreator.create(userId, "1번 가게");
        store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        Store store2 = StoreCreator.create(userId, "2번 가게");
        store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        Store store3 = StoreCreator.create(userId, "3번 가게");
        store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        storeRepository.saveAll(Arrays.asList(store1, store2, store3));

        // when
        List<Store> stores = storeRepository.findAllByUserIdWithScroll(userId, null, 2);

        // then
        assertThat(stores).hasSize(2);
        assertThat(stores.get(0).getName()).isEqualTo("3번 가게");
        assertThat(stores.get(1).getName()).isEqualTo("2번 가게");
    }

    @Test
    void 사용자가_등록한_가게_두번째_페이지_조회() {
        // given
        Long userId = 100L;

        Store store1 = StoreCreator.create(userId, "1번 가게");
        store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        Store store2 = StoreCreator.create(userId, "2번 가게");
        store2.addMenus(Collections.singletonList(MenuCreator.create(store2, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        Store store3 = StoreCreator.create(userId, "3번 가게");
        store3.addMenus(Collections.singletonList(MenuCreator.create(store3, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        storeRepository.saveAll(Arrays.asList(store1, store2, store3));

        // when
        List<Store> stores = storeRepository.findAllByUserIdWithScroll(userId, store2.getId(), 2);

        // then
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getName()).isEqualTo("1번 가게");
    }

    @Test
    void 메뉴가_없는_가게는_조회되지_않는다() {
        // given
        Long userId = 100L;

        Store store1 = StoreCreator.create(userId, "1번 가게");
        store1.addMenus(Collections.singletonList(MenuCreator.create(store1, "붕어빵", "만원", MenuCategoryType.BUNGEOPPANG)));

        Store store2 = StoreCreator.create(userId, "2번 가게");

        storeRepository.saveAll(Arrays.asList(store1, store2));

        // when
        long counts = storeRepository.findCountsByUserId(userId);

        // then
        assertThat(counts).isEqualTo(1);
    }

}
