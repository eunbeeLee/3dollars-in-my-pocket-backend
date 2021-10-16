package com.depromeet.threedollar.domain.domain.review;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RatingGroupTypeTest {

    @MethodSource("source_zero_to_one")
    @ParameterizedTest
    void ZERO_TO_ONE_0점이상_1점미만(double rating) {
        // when
        RatingGroupType reviewGroup = RatingGroupType.of(rating);

        // then
        assertThat(reviewGroup).isEqualTo(RatingGroupType.ZERO_TO_ONE);
    }

    private static Stream<Arguments> source_zero_to_one() {
        return Stream.of(
            Arguments.of(-0.1), // 0점 미만의 예상치 못한 범위가 온 경우 에러보다 이 방법 선택.
            Arguments.of(0),
            Arguments.of(0.9)
        );
    }

    @MethodSource("source_one_to_two")
    @ParameterizedTest
    void ZERO_TO_ONE_1점이상_2점미만(double rating) {
        // when
        RatingGroupType reviewGroup = RatingGroupType.of(rating);

        // then
        assertThat(reviewGroup).isEqualTo(RatingGroupType.ONE_TO_TWO);
    }

    private static Stream<Arguments> source_one_to_two() {
        return Stream.of(
            Arguments.of(1),
            Arguments.of(1.9)
        );
    }

    @MethodSource("source_two_to_three")
    @ParameterizedTest
    void TWO_TO_THREE_2점이상_3점미만(double rating) {
        // when
        RatingGroupType reviewGroup = RatingGroupType.of(rating);

        // then
        assertThat(reviewGroup).isEqualTo(RatingGroupType.TWO_TO_THREE);
    }

    private static Stream<Arguments> source_two_to_three() {
        return Stream.of(
            Arguments.of(2),
            Arguments.of(2.9)
        );
    }

    @MethodSource("source_three_to_four")
    @ParameterizedTest
    void THREE_TO_FOUR_3점이상_4점미만(double rating) {
        // when
        RatingGroupType reviewGroup = RatingGroupType.of(rating);

        // then
        assertThat(reviewGroup).isEqualTo(RatingGroupType.THREE_TO_FOUR);
    }

    private static Stream<Arguments> source_three_to_four() {
        return Stream.of(
            Arguments.of(3),
            Arguments.of(3.9)
        );
    }

    @MethodSource("source_four_to_five")
    @ParameterizedTest
    void FOUR_TO_FIVE_4점이상만(double rating) {
        // when
        RatingGroupType reviewGroup = RatingGroupType.of(rating);

        // then
        assertThat(reviewGroup).isEqualTo(RatingGroupType.FOUR_TO_FIVE);
    }

    private static Stream<Arguments> source_four_to_five() {
        return Stream.of(
            Arguments.of(4),
            Arguments.of(5),
            Arguments.of(5.1) // 5점 이상의 예상치 못한 범위가 온 경우 에러보다 이 방법 선택.
        );
    }
}
