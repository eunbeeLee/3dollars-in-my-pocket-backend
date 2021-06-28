package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RatingTest {

	@Test
	void Rating_of_test() {
		// given
		int value = 4;

		// when
		Rating rating = Rating.of(value);

		// then
		assertThat(rating.getRating()).isEqualTo(value);
	}

	@Test
	void Rating이_1보다_작으면_에러가_발생한다() {
		// given
		int rating = 0;

		// when & then
		assertThatThrownBy(() -> Rating.of(rating)).isInstanceOf(ValidationException.class);
	}

	@Test
	void Rating이_5보다_크면_에러가_발생한다() {
		// given
		int rating = 6;

		// when & then
		assertThatThrownBy(() -> Rating.of(rating)).isInstanceOf(ValidationException.class);
	}

}
