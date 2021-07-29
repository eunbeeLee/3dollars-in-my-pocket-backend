package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.projection.QReviewWithCreatorProjection;
import com.depromeet.threedollar.domain.domain.review.repository.projection.QReviewWithStoreAndCreatorProjection;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithCreatorProjection;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static com.depromeet.threedollar.domain.domain.store.QStore.store;
import static com.depromeet.threedollar.domain.domain.user.QUser.user;
import static com.depromeet.threedollar.domain.domain.review.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Review findReviewByIdAndUserId(Long reviewId, Long userId) {
        return queryFactory.selectFrom(review)
            .where(
                review.id.eq(reviewId),
                review.userId.eq(userId),
                review.status.eq(ReviewStatus.POSTED)
            ).fetchOne();
    }

    @Override
    public List<ReviewWithCreatorProjection> findAllWithCreatorByStoreId(Long storeId) {
        return queryFactory.select(new QReviewWithCreatorProjection(
            review.id,
            review.rating.rating,
            review.contents,
            review.createdAt,
            review.updatedAt,
            user.id,
            user.name,
            user.socialInfo.socialType
        ))
            .from(review)
            .leftJoin(user).on(review.userId.eq(user.id))
            .where(
                review.storeId.eq(storeId),
                review.status.eq(ReviewStatus.POSTED)
            ).fetch();
    }

    @Override
    public List<Review> findAllByStoreId(Long storeId) {
        return queryFactory.selectFrom(review)
            .where(
                review.storeId.eq(storeId),
                review.status.eq(ReviewStatus.POSTED)
            ).fetch();
    }

    /**
     * TODO 차후 스크롤 방식 페이지네이션 고려 필요.
     * 기존의 offset 방식의 페이지네이션을 사용하고 있어서 호환성을 위해 유지하는 중.
     * 성능 최적화를 위해서 커버링 인덱싱을 이용한 방식으로 개선중인데 쿼리가 세번 나가는 중.
     * offset 없이 가능한 페이징 정책인 경우 교체 필요.
     */
    @Override
    public Page<ReviewWithStoreAndCreatorProjection> findAllWithCreatorByUserId(Long userId, Pageable pageable) {
        long totalCount = queryFactory.select(review.id)
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .innerJoin(store).on(review.storeId.eq(store.id))
            .where(
                review.userId.eq(userId),
                review.status.eq(ReviewStatus.POSTED),
                store.status.eq(StoreStatus.ACTIVE)
            )
            .fetchCount();

        if (totalCount == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, totalCount);
        }

        List<Long> reviewIds = queryFactory.select(review.id)
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .innerJoin(store).on(review.storeId.eq(store.id))
            .where(
                review.userId.eq(userId),
                review.status.eq(ReviewStatus.POSTED),
                store.status.eq(StoreStatus.ACTIVE)
            )
            .orderBy(review.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        List<ReviewWithStoreAndCreatorProjection> result = queryFactory.select(new QReviewWithStoreAndCreatorProjection(
            review.id,
            review.rating.rating,
            review.contents,
            review.status,
            review.createdAt,
            review.updatedAt,
            review.storeId,
            store.name,
            user.id,
            user.name,
            user.socialInfo.socialType
        ))
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .innerJoin(store).on(review.storeId.eq(store.id))
            .where(
                review.id.in(reviewIds)
            )
            .orderBy(review.id.desc())
            .fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

}
