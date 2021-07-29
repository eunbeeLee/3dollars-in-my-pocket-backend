package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.menu.QMenu.menu;
import static com.depromeet.threedollar.domain.domain.store.QAppearanceDay.appearanceDay;
import static com.depromeet.threedollar.domain.domain.store.QPaymentMethod.paymentMethod;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * menu, appearanceDay, payment에 대한 fetchJoin()이 필요한대
     * 페치조인은 하나밖에 걸 수 없어서
     * 일단 menu쪽에만 페치조인을 걸어두고
     * batch_fetch_size를 1000으로 둔 상황.
     */
    @Override
    public Store findStoreById(Long storeId) {
        return queryFactory.selectFrom(store)
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

        List<Store> stores = queryFactory.selectFrom(store)
            .leftJoin(store.menus, menu).fetchJoin()
            .where(
                store.id.in(storeIds)
            )
            .orderBy(store.id.desc())
            .fetch();
        return new PageImpl<>(stores, pageRequest, totalCount);
    }

}
