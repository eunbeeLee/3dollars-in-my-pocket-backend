package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Store findStoreById(Long storeId) {
		return queryFactory.selectFrom(store)
				.where(
						store.id.eq(storeId)
				).fetchOne();
	}

}
