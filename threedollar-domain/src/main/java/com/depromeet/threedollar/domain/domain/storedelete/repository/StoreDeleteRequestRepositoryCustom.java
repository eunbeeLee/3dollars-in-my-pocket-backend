package com.depromeet.threedollar.domain.domain.storedelete.repository;

import com.depromeet.threedollar.domain.domain.storedelete.projection.StoreDeleteRequestWithCountProjection;

import java.util.List;

public interface StoreDeleteRequestRepositoryCustom {

    List<Long> findAllUserIdByStoreId(Long storeId);

    List<StoreDeleteRequestWithCountProjection> findStoreHasDeleteRequestMoreThanCnt(int minCount);

}
