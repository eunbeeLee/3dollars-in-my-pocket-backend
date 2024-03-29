package com.depromeet.threedollar.domain.domain.visit.repository;

import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.depromeet.threedollar.domain.domain.visit.VisitHistory;
import com.depromeet.threedollar.domain.domain.visit.projection.QVisitHistoryWithCounts;
import com.depromeet.threedollar.domain.domain.visit.projection.QVisitHistoryWithUserProjection;
import com.depromeet.threedollar.domain.domain.visit.projection.VisitHistoryWithCounts;
import com.depromeet.threedollar.domain.domain.visit.projection.VisitHistoryWithUserProjection;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.depromeet.threedollar.domain.domain.menu.QMenu.menu;
import static com.depromeet.threedollar.domain.domain.visit.QVisitHistory.visitHistory;
import static com.depromeet.threedollar.domain.domain.user.QUser.user;
import static com.depromeet.threedollar.domain.domain.store.QStore.store;

@RequiredArgsConstructor
public class VisitHistoryRepositoryCustomImpl implements VisitHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByStoreIdAndUserIdAndDateOfVisit(Long storeId, Long userId, LocalDate dateOfVisit) {
        return queryFactory.selectOne()
            .from(visitHistory)
            .where(
                visitHistory.store.id.eq(storeId),
                visitHistory.userId.eq(userId),
                visitHistory.dateOfVisit.eq(dateOfVisit)
            ).fetchFirst() != null;
    }

    @Override
    public List<VisitHistoryWithUserProjection> findAllVisitWithUserByStoreIdBetweenDate(Long storeId, LocalDate startDate, LocalDate endDate) {
        return queryFactory.select(new QVisitHistoryWithUserProjection(
                visitHistory.id,
                visitHistory.store.id,
                visitHistory.type,
                visitHistory.dateOfVisit,
                visitHistory.createdAt,
                visitHistory.updatedAt,
                visitHistory.userId,
                user.name,
                user.socialInfo.socialType
            ))
            .from(visitHistory)
            .leftJoin(user).on(visitHistory.userId.eq(user.id))
            .where(
                visitHistory.store.id.eq(storeId),
                visitHistory.dateOfVisit.goe(startDate),
                visitHistory.dateOfVisit.loe(endDate)
            ).fetch();
    }

    @Override
    public List<VisitHistory> findAllByUserIdWithScroll(Long userId, Long lastHistoryId, int size) {
        return queryFactory.selectFrom(visitHistory)
            .innerJoin(visitHistory.store, store).fetchJoin()
            .innerJoin(store.menus, menu).fetchJoin()
            .where(
                visitHistory.userId.eq(userId),
                lessThanId(lastHistoryId),
                store.status.eq(StoreStatus.ACTIVE)
            )
            .orderBy(visitHistory.id.desc())
            .limit(size)
            .fetch();
    }

    private BooleanExpression lessThanId(Long lastHistoryId) {
        if (lastHistoryId == null) {
            return null;
        }
        return store.id.lt(lastHistoryId);
    }

    @Override
    public long findCountsByUserId(Long userId) {
        return queryFactory.selectFrom(visitHistory)
            .innerJoin(visitHistory.store, store)
            .innerJoin(store.menus, menu)
            .where(
                visitHistory.userId.eq(userId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchCount();
    }

    @Override
    public List<VisitHistoryWithCounts> findCountsByStoreIdWithGroup(List<Long> storeIds) {
        return queryFactory.select(new QVisitHistoryWithCounts(visitHistory.store.id, visitHistory.type, visitHistory.id.count()))
            .from(visitHistory)
            .where(
                visitHistory.store.id.in(storeIds)
            )
            .groupBy(visitHistory.store.id, visitHistory.type)
            .fetch();
    }

}
