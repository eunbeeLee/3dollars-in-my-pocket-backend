package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.threedollar.domain.domain.menu.QMenu.menu;
import static com.depromeet.threedollar.domain.domain.store.QAppearanceDay.appearanceDay;
import static com.depromeet.threedollar.domain.domain.store.QPaymentMethod.paymentMethod;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Store findStoreById(Long storeId) {
		return queryFactory.selectFrom(store)
				.leftJoin(store.appearanceDays, appearanceDay)
				.leftJoin(store.paymentMethods, paymentMethod)
				.leftJoin(store.menus, menu)
				.where(
						store.id.eq(storeId),
						store.isDeleted.isFalse()
				).fetchOne();
	}

}
