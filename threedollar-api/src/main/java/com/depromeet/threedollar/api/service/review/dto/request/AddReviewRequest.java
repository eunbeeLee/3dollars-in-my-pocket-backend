package com.depromeet.threedollar.api.service.review.dto.request;

import com.depromeet.threedollar.domain.domain.review.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReviewRequest {

	private String content;

	private int rating;

	public static AddReviewRequest testInstance(String content, int rating) {
		return new AddReviewRequest(content, rating);
	}

	public Review toEntity(Long storeId, Long userId) {
		return Review.of(storeId, userId, content, rating);
	}

}
