package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreatorDto;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithStoreAndCreatorDto;
import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.depromeet.threedollar.domain.domain.user.UserStatusType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public List<ReviewWithCreatorDto> findAllWithCreatorByStoreId(Long storeId) {
        return queryFactory.select(Projections.fields(ReviewWithCreatorDto.class,
            review.id.as("id"),
            review.rating.rating.as("rating"),
            review.contents.as("contents"),
            review.createdAt.as("createdAt"),
            review.updatedAt.as("updatedAt"),
            user.id.as("userId"),
            user.name.as("userName"),
            user.socialInfo.socialType.as("userSocialType")
        ))
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .where(
                review.storeId.eq(storeId),
                user.status.eq(UserStatusType.ACTIVE)
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

    @Override
    public Page<ReviewWithStoreAndCreatorDto> findAllWithCreatorByUserId(Long userId, Pageable pageable) {
        QueryResults<ReviewWithStoreAndCreatorDto> result = queryFactory.select(Projections.fields(ReviewWithStoreAndCreatorDto.class,
            review.id.as("id"),
            review.rating.rating.as("rating"),
            review.contents.as("contents"),
            review.status.as("status"),
            review.createdAt.as("createdAt"),
            review.updatedAt.as("updatedAt"),
            review.storeId.as("storeId"),
            store.storeName.as("storeName"),
            user.id.as("userId"),
            user.name.as("userName"),
            user.socialInfo.socialType.as("userSocialType")
        ))
            .from(review)
            .innerJoin(user).on(review.userId.eq(user.id))
            .innerJoin(store).on(review.storeId.eq(store.id))
            .where(
                review.userId.eq(userId),
                review.status.eq(ReviewStatus.POSTED),
                store.status.eq(StoreStatus.ACTIVE)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

}
