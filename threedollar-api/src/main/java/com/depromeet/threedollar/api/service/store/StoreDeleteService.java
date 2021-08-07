package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository;
import com.depromeet.threedollar.common.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreDeleteService {

    /**
     * MAX_DELETE_REQUEST 만큼 가게 신고가 누적되면 실제로 가게가 삭제가 됩니다.
     */
    private static final int MAX_DELETE_REQUEST = 3;

    private final StoreRepository storeRepository;
    private final StoreDeleteRequestRepository storeDeleteRequestRepository;

    @Transactional
    public boolean delete(Long storeId, DeleteStoreRequest request, Long userId) {
        Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);

        List<Long> userIds = storeDeleteRequestRepository.findAllUserIdByStoreId(storeId);
        if (userIds.contains(userId)) {
            throw new ConflictException(String.format("사용자 (%s)는 가게 (%s)에 대해 이미 삭제 요청을 하였습니다", userId, storeId));
        }
        storeDeleteRequestRepository.save(request.toEntity(storeId, userId));

        if (userIds.size() + 1 >= MAX_DELETE_REQUEST) {
            store.delete();
            return true;
        }
        return false;
    }

}
