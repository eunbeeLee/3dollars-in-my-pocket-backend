package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.exception.ErrorCode;
import com.depromeet.threedollar.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
            throw new ValidationException(String.format("잘못된 Rating 값입니다. (%s)", rating), ErrorCode.VALIDATION_RATING_EXCEPTION);
        }
    }

    public static Rating of(int rating) {
        return new Rating(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return rating == rating1.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }

}
