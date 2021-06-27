package com.depromeet.threedollar.api.service.review.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReviewRequest {

	private String content;

	private int rating;

}
