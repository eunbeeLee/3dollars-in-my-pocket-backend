package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveAroundStoresRequest {

    @NotNull(message = "{store.latitude.notnull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notnull}")
    private Double longitude;

    @NotNull(message = "{store.mapLatitude.notnull}")
    private Double mapLatitude;

    @NotNull(message = "{store.mapLongitude.notnull}")
    private Double mapLongitude;

    @NotNull(message = "{store.distance.notnull}")
    private Double distance;

    public Double getDistance() {
        return this.distance / 1000;
    }

}
