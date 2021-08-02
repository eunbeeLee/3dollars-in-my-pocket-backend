package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuCreator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreTest {

    @Test
    void 대표_카테고리를_선택한다() {
        // given
        Store store = StoreCreator.create(100L, "가게");
        store.addMenus(Arrays.asList(
            MenuCreator.create(store, "name", "price", MenuCategoryType.BUNGEOPPANG),
            MenuCreator.create(store, "name", "price", MenuCategoryType.BUNGEOPPANG),
            MenuCreator.create(store, "name", "price", MenuCategoryType.EOMUK)
        ));

        // when
        List<MenuCategoryType> categories = store.getMenuCategories();

        // then
        assertThat(categories.get(0)).isEqualTo(MenuCategoryType.BUNGEOPPANG);
        assertThat(categories.get(1)).isEqualTo(MenuCategoryType.EOMUK);
    }

    @Test
    void 아무런_메뉴도_없을경우_빈_리스트을_반환한다() {
        // given
        Store store = StoreCreator.create(100L, "가게");

        // when
        List<MenuCategoryType> categories = store.getMenuCategories();

        // then
        assertThat(categories).isEmpty();
    }

}
