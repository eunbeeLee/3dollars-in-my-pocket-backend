package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveAroundStoresRequest {

	private Double latitude;

	private Double longitude;

	private Double mapLatitude;

	private Double mapLongitude;

	private Double distance;

}
