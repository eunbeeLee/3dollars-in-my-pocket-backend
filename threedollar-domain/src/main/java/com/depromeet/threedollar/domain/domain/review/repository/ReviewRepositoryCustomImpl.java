package com.depromeet.threedollar.domain.domain.review.repository;

import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreatorResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.threedollar.domain.domain.review.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Review findReviewById(Long reviewId) {
		return queryFactory.selectFrom(review)
				.where(
						review.id.eq(reviewId),
						review.status.eq(ReviewStatus.POSTED)
				).fetchOne();
	}

	@Override
	public Review findReviewByIdAndUserId(Long reviewId, Long userId) {
		return queryFactory.selectFrom(review)
				.where(
						review.id.eq(reviewId),
						review.userId.eq(userId),
						review.status.eq(ReviewStatus.POSTED)
				).fetchOne();
	}

}
