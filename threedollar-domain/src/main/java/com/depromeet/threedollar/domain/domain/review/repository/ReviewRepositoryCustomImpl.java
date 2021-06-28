package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreator;
import com.depromeet.threedollar.domain.domain.store.StoreStatus;
import com.depromeet.threedollar.domain.domain.user.UserStatusType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
	public List<ReviewWithCreator> findAllReviewWithCreatorByStoreId(Long storeId) {
		return queryFactory.select(Projections.fields(ReviewWithCreator.class,
				review.id.as("id"),
				review.rating.rating.as("rating"),
				review.contents.as("contents"),
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
	public List<Review> findAllReviewByStoreId(Long storeId) {
		return queryFactory.selectFrom(review)
				.where(
						review.storeId.eq(storeId),
						review.status.eq(ReviewStatus.POSTED)
				).fetch();
	}

	@Override
	public List<ReviewWithCreator> findAllReviewWithCreatorByUserId(Long userId) {
		return queryFactory.select(Projections.fields(ReviewWithCreator.class,
				review.id.as("id"),
				review.rating.rating.as("rating"),
				review.contents.as("contents"),
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
				).fetch();
	}

}
