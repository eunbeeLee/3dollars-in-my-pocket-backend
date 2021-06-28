package com.depromeet.threedollar.domain.domain.review.repository.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewWithCreatorResponse {

	private Long id;
	private String contents;
	private int rating;
	private Long storeId;

	private String storeName;

}
