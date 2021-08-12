package com.depromeet.threedollar.domain.domain.common;

import com.depromeet.threedollar.common.exception.validation.ValidationLatitudeException;
import com.depromeet.threedollar.common.exception.validation.ValidationLongitudeException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 대한민국의 위도 / 경도
 * 위도: 북위33 ~ 북위43
 * 경도: 동경124 ~ 동경132
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Location {

    private static final double KOREA_MIN_LATITUDE = 33;
    private static final double KOREA_MAX_LATITUDE = 43;

    private static final double KOREA_MIN_LONGITUDE = 124;
    private static final double KOREA_MAX_LONGITUDE = 132;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    private Location(double latitude, double longitude) {
        validateIsScopeOfKorea(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validateIsScopeOfKorea(double latitude, double longitude) {
        if (latitude < KOREA_MIN_LATITUDE || latitude > KOREA_MAX_LATITUDE) {
            throw new ValidationLatitudeException(String.format("잘못된 위도 (%s)가 입력되었습니다. (33 ~ 43) 사이의 범위만 허용됩니다)", latitude));
        }
        if (longitude < KOREA_MIN_LONGITUDE || longitude > KOREA_MAX_LONGITUDE) {
            throw new ValidationLongitudeException(String.format("잘못된 경도 (%s)가 입력되었습니다. (124 ~ 132) 사이의 범위만 허용됩니다)", longitude));
        }
    }

    public static Location of(double latitude, double longitude) {
        return new Location(latitude, longitude);
    }

}
