package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository;
import com.depromeet.threedollar.domain.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreDeleteService {

    private static final int MAX_DELETE_REQUEST = 3;

    private final StoreRepository storeRepository;
    private final StoreDeleteRequestRepository storeDeleteRequestRepository;

    @Transactional
    public void delete(Long storeId, DeleteStoreRequest request, Long userId) {
        validateNotExistsStoreDeleteRequest(storeId, userId);
        Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
        storeDeleteRequestRepository.save(request.toEntity(store.getId(), userId));
        List<StoreDeleteRequest> storeDeleteRequests = storeDeleteRequestRepository.findAllByStoreId(store.getId());
        if (storeDeleteRequests.size() >= MAX_DELETE_REQUEST) {
            store.delete();
        }
    }

    private void validateNotExistsStoreDeleteRequest(Long storeId, Long userId) {
        StoreDeleteRequest storeDeleteRequest = storeDeleteRequestRepository.findStoreDeleteRequestByStoreIdAndUserId(storeId, userId);
        if (storeDeleteRequest != null) {
            throw new ConflictException(String.format("사용자 (%s)는 가게 (%s)에 대해 이미 삭제 요청을 하였습니다", userId, storeId));
        }
    }

}
