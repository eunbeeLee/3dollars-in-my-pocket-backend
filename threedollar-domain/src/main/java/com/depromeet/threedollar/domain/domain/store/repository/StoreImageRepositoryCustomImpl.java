package com.depromeet.threedollar.domain.domain.store.repository;

import com.depromeet.threedollar.domain.domain.store.StoreImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;
import static com.depromeet.threedollar.domain.domain.store.QStoreImage.storeImage;

@RequiredArgsConstructor
public class StoreImageRepositoryCustomImpl implements StoreImageRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public StoreImage findStoreImageById(Long storeImageId) {
		return queryFactory.selectFrom(storeImage)
				.where(
						storeImage.id.eq(storeImageId)
				).fetchOne();
	}

	@Override
	public List<StoreImage> findStoreImagesByStoreId(Long storeId) {
		return queryFactory.selectFrom(storeImage)
				.innerJoin(storeImage.store, store).fetchJoin()
				.where(
						storeImage.store.id.eq(storeId)
				).fetch();
	}

}
