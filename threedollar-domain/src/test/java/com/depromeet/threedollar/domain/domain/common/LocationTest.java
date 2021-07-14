package com.depromeet.threedollar.domain.domain.common;

import com.depromeet.threedollar.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationTest {

    @Test
    void 위도와_경도를_저장한다() {
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
    void 대한민국_위도를_벗어나면_에러가_발생한다_허용된_위도보다_작은경우() {
        // given
        double latitude = 32.999;
        double longitude = 125.432;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 대한민국_위도를_벗어나면_에러가_발생한다_허용된_위도보다_큰경우() {
        // given
        double latitude = 38.12313;
        double longitude = 43.1;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 대한민국_경도를_벗어나면_에러가_발생한다_허용된_위도보다_작은경우() {
        // given
        double latitude = 34;
        double longitude = 123.9999;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 대한민국_경도를_벗어나면_에러가_발생한다_허용된_위도보다_큰경우() {
        // given
        double latitude = 34;
        double longitude = 132.1;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 동등성_테스트_같은경우_true() {
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
    void 동등성_테스트_위도가_다른경우_false() {
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
    void 동등성_테스트_경도가_다른경우_false() {
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
