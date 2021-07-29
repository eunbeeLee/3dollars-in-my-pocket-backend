package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.menu.QMenu.menu;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;
import static com.querydsl.core.types.dsl.MathExpressions.*;

@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Store findStoreById(Long storeId) {
        return queryFactory.selectFrom(store)
            .where(
                store.id.eq(storeId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
    }

    @Override
    public Store findStoreByIdFetchJoinMenu(Long storeId) {
        return queryFactory.selectFrom(store).distinct()
            .leftJoin(store.menus, menu).fetchJoin()
            .where(
                store.id.eq(storeId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
    }

    @Override
    public Page<Store> findAllByUserIdWithPagination(Long userId, PageRequest pageRequest) {
        long totalCount = queryFactory.select(store.id)
            .from(store)
            .where(
                store.userId.eq(userId)
            )
            .fetchCount();

        List<Long> storeIds = queryFactory.select(store.id)
            .from(store)
            .where(
                store.userId.eq(userId)
            )
            .orderBy(store.id.desc())
            .offset(pageRequest.getOffset())
            .limit(pageRequest.getPageSize())
            .fetch();

        List<Store> stores = queryFactory.selectFrom(store).distinct()
            .leftJoin(store.menus, menu).fetchJoin()
            .where(
                store.id.in(storeIds)
            )
            .orderBy(store.id.desc())
            .fetch();
        return new PageImpl<>(stores, pageRequest, totalCount);
    }

    @Override
    public List<Store> findStoresByLocationLessThanDistance(double latitude, double longitude, double distance) {
        List<Long> storeIds = queryFactory.select(store.id)
            .from(store)
            .groupBy(store.id, store.location.latitude, store.location.longitude)
            .having(Expressions.predicate(Ops.LOE, Expressions.asNumber(getDistanceExpression(latitude, longitude)), Expressions.asNumber(distance)))
            .fetch();

        return queryFactory.selectFrom(store).distinct()
            .leftJoin(store.menus, menu).fetchJoin()
            .where(
                store.id.in(storeIds)
            )
            .fetch();
    }

    private NumberExpression<Double> getDistanceExpression(double latitude, double longitude) {
        return acos(sin(radians(Expressions.constant(latitude)))
            .multiply(sin(radians(store.location.latitude)))
            .add(cos(radians(Expressions.constant(latitude)))
                .multiply(cos(radians(store.location.latitude)))
                .multiply(cos(radians(Expressions.constant(longitude)).subtract(radians(store.location.longitude))))
            )).multiply(6371);
    }

}
