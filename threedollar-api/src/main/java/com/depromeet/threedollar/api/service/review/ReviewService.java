package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.api.service.store.StoreServiceUtils;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final StoreRepository storeRepository;
	private final ReviewRepository reviewRepository;

	@Transactional
	public void addReview(AddReviewRequest request, Long userId) {
		StoreServiceUtils.validateExistsStore(storeRepository, request.getStoreId());
		reviewRepository.save(request.toEntity(userId));
	}

	@Transactional
	public void updateReview(Long reviewId, UpdateReviewRequest request, Long userId) {
		Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
		review.update(request.getContent(), request.getRating());
	}

	@Transactional
	public void deleteReview(Long reviewId, Long userId) {
		Review review = ReviewServiceUtils.findReviewByIdAndUserId(reviewRepository, reviewId, userId);
		review.delete();
	}

	@Transactional(readOnly = true)
	public void getReview(Long reviewId) {

	}

}
