package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.depromeet.threedollar.domain.domain.menu.QMenu.menu;
import static com.depromeet.threedollar.domain.domain.store.QStore.store;

@RequiredArgsConstructor
public class StoreStaticsRepositoryCustomImpl implements StoreStaticsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long findActiveStoresCounts() {
        return queryFactory.select(store.id).distinct()
            .from(store)
            .innerJoin(menu).on(menu.store.id.eq(store.id))
            .where(
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchCount();
    }

    @Override
    public long findActiveStoresCountsBetweenDate(LocalDate startDate, LocalDate endDate) {
        return queryFactory.select(store.id).distinct()
            .from(store)
            .innerJoin(menu).on(menu.store.id.eq(store.id))
            .where(
                store.status.eq(StoreStatus.ACTIVE),
                store.createdAt.goe(startDate.atStartOfDay()),
                store.createdAt.lt(endDate.atStartOfDay().plusDays(1))
            ).fetchCount();
    }

    @Override
    public long findDeletedStoresCountsByDate(LocalDate startDate, LocalDate endDate) {
        return queryFactory.select(store.id).distinct()
            .from(store)
            .innerJoin(menu).on(menu.store.id.eq(store.id))
            .where(
                store.status.eq(StoreStatus.DELETED),
                store.updatedAt.goe(startDate.atStartOfDay()),
                store.updatedAt.lt(endDate.atStartOfDay().plusDays(1))
            ).fetchCount();
    }

}
