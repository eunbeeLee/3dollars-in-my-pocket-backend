package com.depromeet.threedollar.api.event.store;

import com.depromeet.threedollar.api.service.menu.dto.request.MenuRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreCreatedEvent {

	private final Long storeId;

	private final List<MenuRequest> menuRequests;

	public static StoreCreatedEvent of(Long storeId, List<MenuRequest> menuRequests) {
		return new StoreCreatedEvent(storeId, menuRequests);
	}

}
