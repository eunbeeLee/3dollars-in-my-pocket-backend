package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface StoreRepositoryCustom {

	Store findStoreById(Long storeId);

	Page<Store> findAllStoresByUserIdWithPagination(Long userId, PageRequest pageRequest);

}
