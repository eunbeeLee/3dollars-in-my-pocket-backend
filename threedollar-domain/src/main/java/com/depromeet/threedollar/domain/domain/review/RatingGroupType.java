package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.common.exception.model.InternalServerException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RatingGroupType {

    ZERO_TO_ONE(review -> review < 1),
    ONE_TO_TWO(review -> review >= 1 && review < 2),
    TWO_TO_THREE(review -> review >= 2 && review < 3),
    THREE_TO_FOUR(review -> review >= 3 && review < 4),
    FOUR_TO_FIVE(review -> review >= 4);

    private final Predicate<Double> expression;

    public static RatingGroupType of(double rating) {
        return Arrays.stream(values())
            .filter(x -> x.expression.test(rating))
            .findFirst()
            .orElseThrow(() -> new InternalServerException(String.format("예상치 못한 리뷰 범위가 입력되었습니다 (%s)", rating)));
    }

}
