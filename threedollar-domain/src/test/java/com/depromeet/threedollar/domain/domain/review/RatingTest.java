package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RatingTest {

    @Test
    void 평가_점수_값_객체가_생성된다() {
        // given
        int value = 4;

        // when
        Rating rating = Rating.of(value);

        // then
        assertThat(rating.getRating()).isEqualTo(value);
    }

    @Test
    void 점수가_1보다_작으면_VALIDATION_EXEPTION() {
        // given
        int rating = 0;

        // when & then
        assertThatThrownBy(() -> Rating.of(rating)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 점수가_5보다_크면_VALIDATION_EXEPTION() {
        // given
        int rating = 6;

        // when & then
        assertThatThrownBy(() -> Rating.of(rating)).isInstanceOf(ValidationException.class);
    }

    @Test
    void 평가점수_동등성_테스트() {
        // given
        int score = 3;

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
