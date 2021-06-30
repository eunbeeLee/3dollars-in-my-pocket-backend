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
        double latitude = 38.12313;
        double longitude = 123.9999;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }


    @Test
    void 대한민국_경도를_벗어나면_에러가_발생한다_허용된_위도보다_큰경우() {
        // given
        double latitude = 43.1;
        double longitude = 132.1;

        // when & then
        assertThatThrownBy(() -> Location.of(latitude, longitude)).isInstanceOf(ValidationException.class);
    }

}
