package com.depromeet.threedollar.api.service.menu;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

	private final MenuRepository menuRepository;

	public void addMenus(Long storeId, List<MenuRequest> menuRequests) {
		List<Menu> menus = menuRequests.stream()
				.map(menu -> menu.toEntity(storeId))
				.collect(Collectors.toList());
		menuRepository.saveAll(menus);
	}

}
