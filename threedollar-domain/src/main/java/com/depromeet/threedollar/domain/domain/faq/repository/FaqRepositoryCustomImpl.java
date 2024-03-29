package com.depromeet.threedollar.domain.domain.faq.repository;

import com.depromeet.threedollar.domain.domain.faq.Faq;
import com.depromeet.threedollar.domain.domain.faq.FaqCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.threedollar.domain.domain.faq.QFaq.faq;

@RequiredArgsConstructor
public class FaqRepositoryCustomImpl implements FaqRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Faq> findAllByCategory(FaqCategory category) {
        return queryFactory.selectFrom(faq)
            .where(
                eqCategory(category)
            ).fetch();
    }

    private BooleanExpression eqCategory(FaqCategory category) {
        if (category == null) {
            return null;
        }
        return faq.category.eq(category);
    }

    @Override
    public Faq findFaqById(long faqId) {
        return queryFactory.selectFrom(faq)
            .where(
                faq.id.eq(faqId)
            ).fetchOne();
    }

}

