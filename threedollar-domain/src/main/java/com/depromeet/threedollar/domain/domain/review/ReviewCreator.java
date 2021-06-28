package com.depromeet.threedollar.domain.domain.review;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCreator {

	public static Review create(Long storeId, Long userId, String content, int rating) {
		return Review.of(storeId, userId, content, rating);
	}

}
