package com.depromeet.threedollar.domain.domain.user.repository;

import com.depromeet.threedollar.domain.domain.user.WithdrawalUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.threedollar.domain.domain.user.QWithdrawalUser.withdrawalUser;

@RequiredArgsConstructor
public class WithdrawalUserRepositoryCustomImpl implements WithdrawalUserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public WithdrawalUser findWithdrawalUserByUserId(Long userId) {
		return queryFactory.selectFrom(withdrawalUser)
				.where(
						withdrawalUser.userId.eq(userId)
				).fetchOne();
	}

}
