package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.event.store.StoreCreatedEvent;
import com.depromeet.threedollar.api.event.store.StoreUpdatedEvent;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StoreService {

	private final ApplicationEventPublisher eventPublisher;
	private final StoreRepository storeRepository;
	private final StoreDeleteService deleteRequestService;

	@Transactional
	public Long addStore(AddStoreRequest request, Long userId) {
		Store store = storeRepository.save(request.toStore(userId));
		eventPublisher.publishEvent(StoreCreatedEvent.of(store.getId(), request.getMenu()));
		return store.getId();
	}

	@Transactional
	public void updateStore(Long storeId, UpdateStoreRequest request, Long userId) {
		Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
		store.update(request.getLatitude(), request.getLongitude(), request.getStoreName(), store.getStoreType(), userId);
		store.updatePaymentMethods(request.getPaymentMethods());
		store.updateAppearanceDays(request.getAppearanceDays());
		eventPublisher.publishEvent(StoreUpdatedEvent.of(store.getId(), request.getMenu()));
	}

	@Transactional
	public void deleteStore(Long storeId, DeleteStoreRequest request, Long userId) {
		Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
		deleteRequestService.delete(store, request, userId);
	}

}
