package com.depromeet.threedollar.api.service.menu.dto.request;

import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequest {

	private String name;

	private String price;

	private MenuCategoryType category;

	public Menu toEntity(Long storeId) {
		return Menu.newInstance(storeId, name, price, category);
	}

}
