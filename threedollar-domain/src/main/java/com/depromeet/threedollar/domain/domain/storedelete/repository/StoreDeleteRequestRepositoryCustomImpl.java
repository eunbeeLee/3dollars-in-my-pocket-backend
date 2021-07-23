package com.depromeet.threedollar.domain.domain.storedelete.repository;

import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import com.depromeet.threedollar.domain.domain.storedelete.repository.projection.ReportedStoreProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;
import static com.depromeet.threedollar.domain.domain.storedelete.QStoreDeleteRequest.storeDeleteRequest;

@RequiredArgsConstructor
public class StoreDeleteRequestRepositoryCustomImpl implements StoreDeleteRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public StoreDeleteRequest findByStoreIdAndUserId(Long storeId, Long userId) {
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

    @Override
    public List<ReportedStoreProjection> findStoreHasDeleteRequestMoreThanCnt(int minCount) {
        return queryFactory.select(Projections.fields(ReportedStoreProjection.class,
            store.id.as("storeId"),
            store.name.as("storeName"),
            store.location.latitude.as("latitude"),
            store.location.longitude.as("longitude"),
            store.type.as("type"),
            store.rating.as("rating"),
            store.createdAt.as("storeCreatedAt"),
            store.updatedAt.as("storeUpdatedAt"),
            storeDeleteRequest.id.count().as("reportsCount")
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
