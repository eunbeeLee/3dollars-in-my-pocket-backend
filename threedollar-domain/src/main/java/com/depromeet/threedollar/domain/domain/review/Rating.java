package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.common.exception.model.ValidationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static com.depromeet.threedollar.common.exception.ErrorCode.VALIDATION_RATING_EXCEPTION;

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
            throw new ValidationException(String.format("잘못된 Rating 값입니다. (%s)", rating), VALIDATION_RATING_EXCEPTION);
        }
    }

    public static Rating of(int rating) {
        return new Rating(rating);
    }

}
