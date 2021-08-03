package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Nullable;
import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreById(Long storeId);

    Store findStoreByIdFetchJoinMenu(Long storeId);

    Page<Store> findAllByUserIdWithPagination(Long userId, PageRequest pageRequest);

    long findCountsByUserId(Long userId);

    List<Store> findAllByUserIdWithPagination(Long userId, @Nullable Long lastStoreId, int size);

    List<Store> findStoresByLocationLessThanDistance(double latitude, double longitude, double distance);

}
