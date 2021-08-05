package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RatingTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void 평가_점수_값_객체가_생성된다(int value) {
        // when
        Rating rating = Rating.of(value);

        // then
        assertThat(rating.getRating()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void 점수가_1보다_작거나_5이상이면_VALIDATION_EXEPTION(int value) {
        // when & then
        assertThatThrownBy(() -> Rating.of(value)).isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void 평가점수_동등성_테스트(int score) {
        // when
        Rating rating = Rating.of(score);

        // then
        assertThat(rating).isEqualTo(Rating.of(score));
    }

    @Test
    void 평가점수_동등성_테스트_다른_값일경우_False() {
        // when
        Rating rating = Rating.of(4);

        // then
        assertThat(rating).isNotEqualTo(Rating.of(3));
    }

}
