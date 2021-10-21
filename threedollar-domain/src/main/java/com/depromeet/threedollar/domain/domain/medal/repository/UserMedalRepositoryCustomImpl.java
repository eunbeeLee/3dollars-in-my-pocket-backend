package com.depromeet.threedollar.domain.domain.medal.repository;

import com.depromeet.threedollar.domain.domain.medal.UserMedal;
import com.depromeet.threedollar.domain.domain.medal.UserMedalType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.medal.QUserMedal.userMedal;

@RequiredArgsConstructor
public class UserMedalRepositoryCustomImpl implements UserMedalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsMedalByUserId(Long userId, UserMedalType medalType) {
        return queryFactory.selectOne()
            .from(userMedal)
            .where(
                userMedal.userId.eq(userId),
                userMedal.medalType.eq(medalType)
            ).fetchFirst() != null;
    }

    @Override
    public List<UserMedal> findAllByUserId(Long userId) {
        return queryFactory.selectFrom(userMedal)
            .where(
                userMedal.userId.eq(userId)
            ).fetch();
    }

}
