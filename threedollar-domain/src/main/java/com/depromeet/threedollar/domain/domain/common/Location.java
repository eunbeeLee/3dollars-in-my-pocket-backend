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

}
