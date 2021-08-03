package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;

import javax.annotation.Nullable;
import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreById(Long storeId);

    Store findStoreByIdFetchJoinMenu(Long storeId);

    long findCountsByUserId(Long userId);

    List<Store> findAllByUserIdWithScroll(Long userId, @Nullable Long lastStoreId, int size);

    List<Store> findStoresByLocationLessThanDistance(double latitude, double longitude, double distance);

}
