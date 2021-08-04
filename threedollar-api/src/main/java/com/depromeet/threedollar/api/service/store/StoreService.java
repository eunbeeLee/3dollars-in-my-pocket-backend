package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDeleteResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreSummaryResponse;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreDeleteService deleteRequestService;

    @Transactional
    public StoreSummaryResponse addStore(AddStoreRequest request, Long userId) {
        Store store = storeRepository.save(request.toStore(userId));
        return StoreSummaryResponse.of(store);
    }

    @Transactional
    public StoreSummaryResponse updateStore(Long storeId, UpdateStoreRequest request, Long userId) {
        Store store = StoreServiceUtils.findStoreByIdFetchJoinMenu(storeRepository, storeId);
        store.updateLocation(request.getLatitude(), request.getLongitude());
        store.updateInfo(request.getStoreName(), request.getStoreType(), userId);
        store.updatePaymentMethods(request.getPaymentMethods());
        store.updateAppearanceDays(request.getAppearanceDays());
        store.updateMenu(request.toMenus(store));
        return StoreSummaryResponse.of(store);
    }

    @Transactional
    public StoreDeleteResponse deleteStore(Long storeId, DeleteStoreRequest request, Long userId) {
        return StoreDeleteResponse.of(deleteRequestService.delete(storeId, request, userId));
    }

}
