package com.depromeet.threedollar.domain.domain.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

	private Double latitude;

	private Double longitude;

	private Location(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static Location of(Double latitude, Double longitude) {
		return new Location(latitude, longitude);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return Objects.equals(latitude, location.latitude) && Objects.equals(longitude, location.longitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

}
