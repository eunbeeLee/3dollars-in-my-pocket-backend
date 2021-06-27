package com.depromeet.threedollar.api.service.review.dto.request;

import com.depromeet.threedollar.domain.domain.review.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReviewRequest {

	private Long storeId;

	private String content;

	private int rating;

	public Review toEntity(Long userId) {
		return Review.of(storeId, userId, content, rating);
	}

}
