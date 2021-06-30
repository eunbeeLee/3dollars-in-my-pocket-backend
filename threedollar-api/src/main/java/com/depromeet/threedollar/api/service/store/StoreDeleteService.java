package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository;
import com.depromeet.threedollar.common.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreDeleteService {

    // MAX_DELETE_REQUEST 만큼 가게 신고가 누적되면 실제로 가게가 삭제가 됩니다.
    private static final int MAX_DELETE_REQUEST = 3;

    private final StoreRepository storeRepository;
    private final StoreDeleteRequestRepository storeDeleteRequestRepository;

    @Transactional
    public void delete(Long storeId, DeleteStoreRequest request, Long userId) {
        validateNotExistsStoreDeleteRequest(storeId, userId);
        Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
        storeDeleteRequestRepository.save(request.toEntity(store.getId(), userId));
        deleteStoreIfAccumulatedDeleteRequests(store);
    }

    private void validateNotExistsStoreDeleteRequest(Long storeId, Long userId) {
        StoreDeleteRequest storeDeleteRequest = storeDeleteRequestRepository.findByStoreIdAndUserId(storeId, userId);
        if (storeDeleteRequest != null) {
            throw new ConflictException(String.format("사용자 (%s)는 가게 (%s)에 대해 이미 삭제 요청을 하였습니다", userId, storeId));
        }
    }

    private void deleteStoreIfAccumulatedDeleteRequests(Store store) {
        List<StoreDeleteRequest> storeDeleteRequests = storeDeleteRequestRepository.findAllByStoreId(store.getId());
        if (storeDeleteRequests.size() >= MAX_DELETE_REQUEST) {
            store.delete();
        }
    }

}
