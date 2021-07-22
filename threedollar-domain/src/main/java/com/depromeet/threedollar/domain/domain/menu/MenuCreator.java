package com.depromeet.threedollar.domain.domain.menu;

import com.depromeet.threedollar.domain.domain.store.Store;

public final class MenuCreator {

    public static Menu create(Store store, String name, String price, MenuCategoryType category) {
        return Menu.of(store, name, price, category);
    }

}
