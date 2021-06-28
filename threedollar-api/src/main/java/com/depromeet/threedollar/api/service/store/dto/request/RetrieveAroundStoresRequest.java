package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveAroundStoresRequest {

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotNull
	private Double mapLatitude;

	@NotNull
	private Double mapLongitude;

	@NotNull
	private Double distance;

	public Double getDistance() {
		return this.distance / 1000;
	}

}
