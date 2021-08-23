package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.projection.StoreWithReportedCountProjection;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreById(Long storeId);

    Store findStoreByIdFetchJoinMenu(Long storeId);

    long findCountsByUserId(Long userId);

    List<Store> findAllByUserIdWithScroll(Long userId, Long lastStoreId, int size);

    List<Store> findAllByIds(List<Long> storeIds);

    List<Store> findStoresByLocationLessThanDistance(double latitude, double longitude, double distance);

    List<StoreWithReportedCountProjection> findStoresByMoreThanReportCntWithPagination(int cnt, long offset, int size);

}
