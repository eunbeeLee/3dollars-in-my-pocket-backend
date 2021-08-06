package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.common.exception.ErrorCode;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class StoreServiceUtils {

    static void validateExistsStore(StoreRepository storeRepository, Long storeId) {
        Store store = storeRepository.findStoreById(storeId);
        if (store == null) {
            throw new NotFoundException(String.format("해당하는 가게 (%s)는 존재하지 않습니다", storeId), ErrorCode.NOT_FOUND_STORE_EXCEPTION);
        }
    }

    static Store findStoreById(StoreRepository storeRepository, Long storeId) {
        Store store = storeRepository.findStoreById(storeId);
        if (store == null) {
            throw new NotFoundException(String.format("해당하는 가게 (%s)는 존재하지 않습니다", storeId), ErrorCode.NOT_FOUND_STORE_EXCEPTION);
        }
        return store;
    }

    static Store findStoreByIdFetchJoinMenu(StoreRepository storeRepository, Long storeId) {
        Store store = storeRepository.findStoreByIdFetchJoinMenu(storeId);
        if (store == null) {
            throw new NotFoundException(String.format("해당하는 가게 (%s)는 존재하지 않습니다", storeId), ErrorCode.NOT_FOUND_STORE_EXCEPTION);
        }
        return store;
    }

}
