package com.depromeet.threedollar.api.controller.menu;

import com.depromeet.threedollar.api.event.store.StoreCreatedEvent;
import com.depromeet.threedollar.api.event.store.StoreUpdatedEvent;
import com.depromeet.threedollar.api.service.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MenuEventListener {

	private final MenuService menuService;

	@EventListener
	public void addMenus(StoreCreatedEvent event) {
		menuService.addMenus(event.getStoreId(), event.getMenuRequests());
	}

	@EventListener
	public void updateMenus(StoreUpdatedEvent event) {
		menuService.updateMenus(event.getStoreId(), event.getMenuRequests());
	}

}
