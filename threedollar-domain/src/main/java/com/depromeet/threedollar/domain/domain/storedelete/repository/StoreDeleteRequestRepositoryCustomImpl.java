package com.depromeet.threedollar.domain.domain.storedelete.repository;

import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.depromeet.threedollar.domain.domain.storedelete.projection.QStoreDeleteRequestWithCountProjection;
import com.depromeet.threedollar.domain.domain.storedelete.projection.StoreDeleteRequestWithCountProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;
import static com.depromeet.threedollar.domain.domain.storedelete.QStoreDeleteRequest.storeDeleteRequest;

@RequiredArgsConstructor
public class StoreDeleteRequestRepositoryCustomImpl implements StoreDeleteRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findAllUserIdByStoreId(Long storeId) {
        return queryFactory.select(storeDeleteRequest.userId)
            .from(storeDeleteRequest)
            .where(
                storeDeleteRequest.storeId.eq(storeId)
            ).fetch();
    }

    @Override
    public List<StoreDeleteRequestWithCountProjection> findStoreHasDeleteRequestMoreThanCnt(int minCount) {
        return queryFactory.select(new QStoreDeleteRequestWithCountProjection(
            store.id,
            store.name,
            store.location.latitude,
            store.location.longitude,
            store.type,
            store.rating,
            store.createdAt,
            store.updatedAt,
            storeDeleteRequest.id.count()
        ))
            .from(storeDeleteRequest)
            .innerJoin(store).on(storeDeleteRequest.storeId.eq(store.id))
            .where(
                store.status.eq(StoreStatus.ACTIVE)
            )
            .groupBy(store.id)
            .having(store.id.count().goe(minCount))
            .orderBy(store.id.count().desc())
            .fetch();
    }

}
