package com.depromeet.threedollar.api.service.menu;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuServiceTest {

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuRepository menuRepository;

	@AfterEach
	void cleanUp() {
		menuRepository.deleteAll();
	}

	@Test
	void 가게에_메뉴들을_추가한다() {
		// given
		Long storeId = 1L;
		MenuRequest menuRequest1 = MenuRequest.of("붕어빠아아앙", "만원", MenuCategoryType.BUNGEOPPANG);
		MenuRequest menuRequest2 = MenuRequest.of("어무우욱", "오천원", MenuCategoryType.EOMUK);
		List<MenuRequest> menuRequestList = Arrays.asList(menuRequest1, menuRequest2);

		// when
		menuService.addMenus(storeId, menuRequestList);

		// then
		List<Menu> menus = menuRepository.findAll();
		assertThat(menus).hasSize(2);
		assertMenu(menus.get(0), menuRequest1.getName(), menuRequest1.getPrice(), menuRequest1.getCategory());
		assertMenu(menus.get(1), menuRequest2.getName(), menuRequest2.getPrice(), menuRequest2.getCategory());
	}

	@Test
	void 가게에_등록된_메뉴들을_수정하면_기존_메뉴들은_삭제되고_새로운_메뉴들이_등록된다() {
		// given
		Long storeId = 1L;
		menuRepository.save(Menu.newInstance(storeId, "기존 이름", "기존 가격", MenuCategoryType.BUNGEOPPANG));

		MenuRequest menuRequest1 = MenuRequest.of("붕어빠아아앙", "만원", MenuCategoryType.BUNGEOPPANG);
		MenuRequest menuRequest2 = MenuRequest.of("어무우욱", "오천원", MenuCategoryType.EOMUK);
		List<MenuRequest> menuRequestList = Arrays.asList(menuRequest1, menuRequest2);

		// when
		menuService.updateMenus(storeId, menuRequestList);

		// then
		List<Menu> menus = menuRepository.findAll();
		assertThat(menus).hasSize(2);
		assertMenu(menus.get(0), menuRequest1.getName(), menuRequest1.getPrice(), menuRequest1.getCategory());
		assertMenu(menus.get(1), menuRequest2.getName(), menuRequest2.getPrice(), menuRequest2.getCategory());
	}

	private void assertMenu(Menu menu, String name, String price, MenuCategoryType category) {
		assertThat(menu.getName()).isEqualTo(name);
		assertThat(menu.getPrice()).isEqualTo(price);
		assertThat(menu.getCategory()).isEqualTo(category);
	}

}