package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.event.store.StoreCreatedEvent;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {

	private final ApplicationEventPublisher eventPublisher;
	private final StoreRepository storeRepository;

	public void addStore(AddStoreRequest request, Long userId) {
		Store store = storeRepository.save(request.toStore(userId));
		eventPublisher.publishEvent(StoreCreatedEvent.of(store.getId(), request.getMenu()));
	}

	public void updateStore(UpdateStoreRequest request, Long userId) {
		Store store = StoreServiceUtils.findStoreById(storeRepository, request.getStoreId());
		store.update(request.getLatitude(), request.getLongitude(), request.getStoreName(), store.getStoreType());
	}

}
