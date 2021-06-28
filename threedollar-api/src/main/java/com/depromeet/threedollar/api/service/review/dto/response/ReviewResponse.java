package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse {

	private Long id;

	private int rating;

	private String contents;

	private UserInfoResponse user;

	public static ReviewResponse of(ReviewWithCreator review) {
		return new ReviewResponse(review.getId(), review.getRating(), review.getContents(), UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()));
	}

}
