package com.depromeet.threedollar.domain.domain.common;

import com.depromeet.threedollar.common.exception.model.ValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 인스턴스_생성 {

        @MethodSource("latitude_longitude")
        @ParameterizedTest
        void 위도와_경도로_이루어진_위치_값_객체를_생성한다(double latitude, double longitude) {
            // when
            Location location = Location.of(latitude, longitude);

            // then
            assertThat(location.getLatitude()).isEqualTo(latitude);
            assertThat(location.getLongitude()).isEqualTo(longitude);
        }

        private Stream<Arguments> latitude_longitude() {
            return Stream.of(
                Arguments.of(33, 124),
                Arguments.of(43, 132)
            );
        }

        @MethodSource("wrong_latitude")
        @ParameterizedTest
        void 허용된_위도_범위_밖인경우_VALIDATION_LATITUDE_EXEPTION(double latitude, double longitude) {
            // when & then
            assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
        }

        private Stream<Arguments> wrong_latitude() {
            return Stream.of(
                Arguments.of(32.999, 124),
                Arguments.of(43.1, 124)
            );
        }

        @MethodSource("wrong_longitude")
        @ParameterizedTest
        void 허용된_경도_범위_밖인경우_VALIDATION_LATITUDE_EXEPTION(double latitude, double longitude) {
            // when & then
            assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
        }

        private Stream<Arguments> wrong_longitude() {
            return Stream.of(
                Arguments.of(33, 132.1),
                Arguments.of(33, 123.9)
            );
        }

    }

    @Nested
    class 동등성_테스트 {

        @Test
        void Location_동등성_테스트_같은경우_같은_객체로_판단() {
            // given
            double latitude = 38.12313;
            double longitude = 125.432;

            Location source = Location.of(latitude, longitude);
            Location target = Location.of(latitude, longitude);

            // when
            boolean result = source.equals(target);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void Location_동등성_테스트_위도가_다른경우_다른_객체로_판단() {
            // given
            double longitude = 125.432;

            Location source = Location.of(38.123, longitude);
            Location target = Location.of(38.124, longitude);

            // when
            boolean result = source.equals(target);

            // then
            assertThat(result).isFalse();
        }

        @Test
        void Location_동등성_테스트_경도가_다른경우_다른_객체로_판단() {
            // given
            double latitude = 38.12313;

            Location source = Location.of(latitude, 125.432);
            Location target = Location.of(latitude, 125.433);

            // when
            boolean result = source.equals(target);

            // then
            assertThat(result).isFalse();
        }

    }

}
