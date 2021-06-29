package com.depromeet.threedollar.domain.domain.storedelete.repository;

import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.storedelete.QStoreDeleteRequest.storeDeleteRequest;

@RequiredArgsConstructor
public class StoreDeleteRequestRepositoryCustomImpl implements StoreDeleteRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public StoreDeleteRequest findStoreDeleteRequestByStoreIdAndUserId(Long storeId, Long userId) {
        return queryFactory.selectFrom(storeDeleteRequest)
            .where(
                storeDeleteRequest.storeId.eq(storeId),
                storeDeleteRequest.userId.eq(userId)
            ).fetchOne();
    }

    @Override
    public List<StoreDeleteRequest> findAllByStoreId(Long storeId) {
        return queryFactory.selectFrom(storeDeleteRequest)
            .where(
                storeDeleteRequest.storeId.eq(storeId)
            ).fetch();
    }

}
