package com.depromeet.threedollar.domain.domain.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

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

}
