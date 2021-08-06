package com.depromeet.threedollar.common.type;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceGroupTypeTest {

    @MethodSource("source_under_fifty")
    @ParameterizedTest
    void UNDER_FIFTY_50미만인경우(double distance) {
        // when
        DistanceGroupType type = DistanceGroupType.of(distance);

        // then
        assertThat(type).isEqualTo(DistanceGroupType.UNDER_FIFTY);
    }

    private static Stream<Arguments> source_under_fifty() {
        return Stream.of(
            Arguments.of(49.9),
            Arguments.of(0),
            Arguments.of(-1)
        );
    }

    @MethodSource("source_fifty_to_hundred")
    @ParameterizedTest
    void FIFTY_TO_HUNDRED_50이상_100미만인경우(double distance) {
        // when
        DistanceGroupType type = DistanceGroupType.of(distance);

        // then
        assertThat(type).isEqualTo(DistanceGroupType.FIFTY_TO_HUNDRED);
    }

    private static Stream<Arguments> source_fifty_to_hundred() {
        return Stream.of(
            Arguments.of(50),
            Arguments.of(99)
        );
    }

    @MethodSource("source_hundred_to_five_hundred")
    @ParameterizedTest
    void HUNDRED_TO_FIVE_HUNDRED_100이상_500미만인경우(double distance) {
        // when
        DistanceGroupType type = DistanceGroupType.of(distance);

        // then
        assertThat(type).isEqualTo(DistanceGroupType.HUNDRED_TO_FIVE_HUNDRED);
    }

    private static Stream<Arguments> source_hundred_to_five_hundred() {
        return Stream.of(
            Arguments.of(100),
            Arguments.of(499)
        );
    }

    @MethodSource("source_five_hundred_to_thousand")
    @ParameterizedTest
    void FIVE_HUNDRED_TO_THOUSAND_500이상_1000미만인경우(double distance) {
        // when
        DistanceGroupType type = DistanceGroupType.of(distance);

        // then
        assertThat(type).isEqualTo(DistanceGroupType.FIVE_HUNDRED_TO_THOUSAND);
    }

    private static Stream<Arguments> source_five_hundred_to_thousand() {
        return Stream.of(
            Arguments.of(500),
            Arguments.of(999)
        );
    }

    @MethodSource("source_over_thousand")
    @ParameterizedTest
    void OVER_THOUSAND_1000이상인경우(double distance) {
        // when
        DistanceGroupType type = DistanceGroupType.of(distance);

        // then
        assertThat(type).isEqualTo(DistanceGroupType.OVER_THOUSAND);
    }

    private static Stream<Arguments> source_over_thousand() {
        return Stream.of(
            Arguments.of(1000)
        );
    }

}
