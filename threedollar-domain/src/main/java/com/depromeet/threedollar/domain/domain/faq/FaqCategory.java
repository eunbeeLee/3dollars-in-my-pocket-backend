package com.depromeet.threedollar.domain.domain.faq;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FaqCategory {

	STORE("가게"),
	REVIEW_MENU("리뷰 및 메뉴"),
	WITHDRAWAL("회원 탈퇴"),
	BOARD("게시글"),
	CATEGORY("카테고리");

	private final String description;

}
