package com.depromeet.threedollar.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void 소수점_둘쨰_자리에서_반올림한다_올림인경우() {
        // given
        double value = 3.75111;

        // when
        double result = MathUtils.round(value);

        // then
        assertThat(result).isEqualTo(3.8);
    }

    @Test
    void 소수점_둘쨰_자리에서_반올림한다_내림인_겨우() {
        // given
        double value = 3.749999;

        // when
        double result = MathUtils.round(value);

        // then
        assertThat(result).isEqualTo(3.7);
    }

    @Test
    void ZERO_인경우_0이된다() {
        // given
        double value = 0.0;

        // when
        double result = MathUtils.round(value);

        // then
        assertThat(result).isEqualTo(0);
    }

}
