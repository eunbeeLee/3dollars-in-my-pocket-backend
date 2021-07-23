package com.depromeet.threedollar.domain.domain.storedelete.repository;

import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import com.depromeet.threedollar.domain.domain.storedelete.repository.projection.ReportedStoreProjection;

import java.util.List;

public interface StoreDeleteRequestRepositoryCustom {

    StoreDeleteRequest findByStoreIdAndUserId(Long storeId, Long userId);

    List<StoreDeleteRequest> findAllByStoreId(Long storeId);

    List<ReportedStoreProjection> findStoreHasDeleteRequestMoreThanCnt(int minCount);

}
