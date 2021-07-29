package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreById(Long storeId);

    Store findStoreByIdFetchJoinMenu(Long storeId);

    Page<Store> findAllByUserIdWithPagination(Long userId, PageRequest pageRequest);

    List<Store> findStoresByLocationLessThanDistance(double latitude, double longitude, double distance);

}
