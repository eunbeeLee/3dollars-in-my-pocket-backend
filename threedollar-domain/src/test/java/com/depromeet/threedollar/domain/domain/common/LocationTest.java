package com.depromeet.threedollar.domain.domain.common;

import com.depromeet.threedollar.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationTest {

    @Test
    void 위도와_경도로_이루어진_위치_값_객체를_생성한다() {
        // given
        double latitude = 38.12313;
        double longitude = 125.432;

        // when
        Location location = Location.of(latitude, longitude);

        // then
        assertThat(location.getLatitude()).isEqualTo(latitude);
        assertThat(location.getLongitude()).isEqualTo(longitude);
    }

    @Test
    void 허용된_위도보다_작은경우_VALIDATION_EXEPTION() {
        // given
        double latitude = 32.999;
        double longitude = 125;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 허용된_위도보다_큰경우_VALIDATION_EXEPTION() {
        // given
        double latitude = 43.1;
        double longitude = 125;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 허용된_경도보다_작은경우_VALIDATION_EXEPTION() {
        // given
        double latitude = 40;
        double longitude = 123.9;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 허용된_경도보다_큰경우_VALIDATION_EXEPTION() {
        // given
        double latitude = 40;
        double longitude = 132.1;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

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
