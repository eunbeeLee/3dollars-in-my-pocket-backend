package com.depromeet.threedollar.domain.domain.faq;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FaqCategory {

    STORE("가게", 1),
    REVIEW_MENU("리뷰 및 메뉴", 2),
    WITHDRAWAL("회원 탈퇴", 3),
    BOARD("게시글", 4),
    CATEGORY("카테고리", 5);

    private final String description;
    private final int displayOrder;

}
