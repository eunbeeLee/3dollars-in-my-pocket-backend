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

    /**
     * OneToMany에서는 다중 FetchJoin 불가.
     * -
     * Store를 조회할때 [menu, appearanceDays, payments]를 oneToMany로 조회해서 N+1가 발생하는 이슈로
     * menu만 fetchJoin을 걸어두고 (cuz menu만 필요한 쿼리들이 1/2로 존재하는 이유로 menu에 페치조인을 검)
     * -
     * default_batch_fetch_size: 1000 으로 설정하며
     * 1000개 칼럼씩 WHERE store_id IN (...)으로 조회해서 N+1 문제를 해결하는 중.
     */
    @Override
    public Store findStoreByIdFetchJoinMenu(Long storeId) {
        return queryFactory.selectFrom(store).distinct()
            .leftJoin(store.menus, menu).fetchJoin()
            .where(
                store.id.eq(storeId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
    }

    /**
     * TODO 차후 스크롤 방식 페이지네이션 고려 필요.
     * 기존의 offset 방식의 페이지네이션을 사용하고 있어서 호환성을 위해 유지하는 중.
     * 성능 최적화를 위해서 커버링 인덱싱을 이용한 방식으로 개선중인데 쿼리가 세번 나가는 중.
     * offset 없이 가능한 페이징 정책인 경우 교체 필요.
     */
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

    /**
     * TODO  B-Tree의 공간정보 인덱스 한계로, R-Tree 인덱스 리서치 필요.
     * 현재 인덱스 풀스캔 (커버링 인덱스) -> PK들을 통한 LEFT JOIN을 통해서 계산하고 있음.
     */
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

    /**
     * 위도 (latitude), 경도 (longitude)가 주어졌을때 거리 계산 공식.
     * -
     * 6371 * acos(cos(radians(:latitude)) * cos(radians(store.latitude)) * cos(radians(store.longitude)
     * - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(store.latitude)))) as distance
     */
    private NumberExpression<Double> getDistanceExpression(double latitude, double longitude) {
        return acos(sin(radians(Expressions.constant(latitude)))
            .multiply(sin(radians(store.location.latitude)))
            .add(cos(radians(Expressions.constant(latitude)))
                .multiply(cos(radians(store.location.latitude)))
                .multiply(cos(radians(Expressions.constant(longitude)).subtract(radians(store.location.longitude))))
            )).multiply(6371);
    }

}
