package com.depromeet.threedollar.domain.domain.review;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewStatus {

	POSTED(true),
	FILTERED(false),
	DELETED(false);

	private final boolean visible;

}
