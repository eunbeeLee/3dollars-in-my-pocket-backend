package com.depromeet.threedollar.domain.domain.menu.repository;

import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.menu.QMenu.menu;

@RequiredArgsConstructor
public class MenuRepositoryCustomImpl implements MenuRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Menu> findAllByStoreId(Long storeId) {
		return queryFactory.selectFrom(menu)
				.where(
						menu.storeId.eq(storeId)
				).fetch();
	}

}
