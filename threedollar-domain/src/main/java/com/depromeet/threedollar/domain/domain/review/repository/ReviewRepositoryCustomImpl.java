package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.projection.QReviewWithWriterProjection;
import com.depromeet.threedollar.domain.domain.review.projection.ReviewWithWriterProjection;
import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.LockModeType;
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

    /**
     * 특정 가게의 모든 리뷰를 가져와서 가게의 평균 리뷰 점수를 계산할때, 동시성 문제가 발생할 수 있음.
     * MySQL InnoDB (serializable isolation level 제외)에서는 SELECT시 잠금을 걸지 않음 (MVCC로 언두 로그를 읽음)
     * 1. [ThreadA] SELECT store's reviews (10개의 리뷰)
     * 2. [ThreadB] SELECT store's reviews (10개의 리뷰)
     * 3. (Lock) [ThreadA] UPDATE Store (UnLock by commit or rollback): (10개의 리뷰 + ThreadA 변경사항 적용)
     * 4. (Lock) [ThreadB] UPDATE Store (UnLock by commit or rollback): (10개의 리뷰 + ThreadB 변경사항 적용) - 여기서 ThreadA의 변경사항이 적용되지 않는 문제 발생
     * -
     * 해결 방법
     * 1. [ThreadA] SELECT store's reviews with 쓰기 모드 잠금 (for update)
     * 2. [ThreadA] UPDATE Store (UnLock by commit or rollback): (10개의 리뷰 + ThreadA 변경사항 적용)
     * 3. [ThreadB] SELECT store's reviews with 쓰기 모드 잠금 (for update)
     * 4. [ThreadB] UPDATE Store (UnLock by commit or rollback): (10개의 리뷰 + ThreadA + ThreadB 변경사항 적용)
     */
    @Override
    public List<Review> findAllByStoreId(Long storeId) {
        return queryFactory.selectFrom(review)
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .setHint("javax.persistence.lock.timeout", 2000)
            .where(
                review.storeId.eq(storeId),
                review.status.eq(ReviewStatus.POSTED)
            )
            .fetch();
    }

    @Override
    public long findCountsByUserId(Long userId) {
        return queryFactory.select(review.id)
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .innerJoin(store).on(review.storeId.eq(store.id))
            .where(
                review.userId.eq(userId),
                review.status.eq(ReviewStatus.POSTED),
                store.status.eq(StoreStatus.ACTIVE)
            )
            .fetchCount();
    }

    @Override
    public List<ReviewWithWriterProjection> findAllWithCreatorByStoreId(Long storeId) {
        return queryFactory.select(new QReviewWithWriterProjection(
            review.id,
            review.rating.rating,
            review.contents,
            review.createdAt,
            review.updatedAt,
            review.storeId,
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
    public List<ReviewWithWriterProjection> findAllByUserIdWithScroll(Long userId, Long lastStoreId, int size) {
        List<Long> reviewIds = queryFactory.select(review.id)
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .innerJoin(store).on(review.storeId.eq(store.id))
            .where(
                review.userId.eq(userId),
                review.status.eq(ReviewStatus.POSTED),
                store.status.eq(StoreStatus.ACTIVE),
                lessThanId(lastStoreId)
            )
            .orderBy(review.id.desc())
            .limit(size)
            .fetch();

        return queryFactory.select(new QReviewWithWriterProjection(
            review.id,
            review.rating.rating,
            review.contents,
            review.createdAt,
            review.updatedAt,
            review.storeId,
            user.id,
            user.name,
            user.socialInfo.socialType
        ))
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .where(
                review.id.in(reviewIds)
            )
            .orderBy(review.id.desc())
            .fetch();
    }

    private BooleanExpression lessThanId(Long lastStoreId) {
        if (lastStoreId == null) {
            return null;
        }
        return review.id.lt(lastStoreId);
    }

}
