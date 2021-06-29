package com.depromeet.threedollar.api.service.review.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveMyReviewsRequest {

	@Min(value = 0,message = "{review.page.min}")
	private int page;

	@Min(value = 1,message = "{review.size.min}")
	private int size;

}
