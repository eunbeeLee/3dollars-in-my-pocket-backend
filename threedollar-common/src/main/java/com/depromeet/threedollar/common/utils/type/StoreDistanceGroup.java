package com.depromeet.threedollar.common.utils.type;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum StoreDistanceGroup {

    UNDER_FIFTY(distance -> distance < 50),
    FIFTY_TO_HUNDRED(distance -> distance >= 50 && distance < 100),
    HUNDRED_TO_FIVE_HUNDRED(distance -> distance > 100 && distance < 500),
    FIVE_HUNDRED_TO_THOUSAND(distance -> distance >= 500 && distance < 1000),
    OVER_THOUSAND(distance -> distance >= 1000);

    private final Predicate<Double> expression;

    public static StoreDistanceGroup of(double distance) {
        return Arrays.stream(values())
            .filter(x -> x.expression.test(distance))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("예상치 못한 범위가 입력되었습니다 (%s)", distance)));
    }

}