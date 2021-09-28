package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.common.exception.model.validation.ValidationRatingException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Rating {

    private static final int MIN_RATING_VALUE = 1;
    private static final int MAX_RATING_VALUE = 5;

    @Column(nullable = false)
    private int rating;

    private Rating(int rating) {
        validateRating(rating);
        this.rating = rating;
    }

    private void validateRating(int rating) {
        if (rating < MIN_RATING_VALUE || rating > MAX_RATING_VALUE) {
            throw new ValidationRatingException(String.format("잘못된 Rating 값입니다. (%s)", rating));
        }
    }

    public static Rating of(int rating) {
        return new Rating(rating);
    }

}
