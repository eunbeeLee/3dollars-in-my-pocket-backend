package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.StoreImage;

import java.util.List;

public interface StoreImageRepositoryCustom {

    StoreImage findStoreImageById(Long storeImageId);

    List<StoreImage> findStoreImagesByStoreId(Long storeId);

}
