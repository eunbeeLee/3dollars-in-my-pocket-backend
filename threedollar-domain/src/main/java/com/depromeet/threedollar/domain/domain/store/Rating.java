package com.depromeet.threedollar.domain.domain.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Rating {

	private Double rating;

	// TODO Rating에 대한 Validation이 필요함.
}
