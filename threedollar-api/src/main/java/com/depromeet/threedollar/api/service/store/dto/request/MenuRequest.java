package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequest {

	private String name;

	private String price;

	private MenuCategoryType category;

	public static MenuRequest of(String name, String price, MenuCategoryType category) {
		return new MenuRequest(name, price, category);
	}

	public Menu toEntity(Store store) {
		return Menu.of(store, name, price, category);
	}

}
