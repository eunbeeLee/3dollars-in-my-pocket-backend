package com.depromeet.threedollar.api.service.menu;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

	private final MenuRepository menuRepository;

	@Transactional
	public void addMenus(Long storeId, List<MenuRequest> menuRequests) {
		List<Menu> menus = menuRequests.stream()
				.map(menu -> menu.toEntity(storeId))
				.collect(Collectors.toList());
		menuRepository.saveAll(menus);
	}

	@Transactional
	public void updateMenus(Long storeId, List<MenuRequest> menuRequests) {
		List<Menu> menus = menuRepository.findAllByStoreId(storeId);
		menuRepository.deleteAll(menus);
		addMenus(storeId, menuRequests);
	}

}
