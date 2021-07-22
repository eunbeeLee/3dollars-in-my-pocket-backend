package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static RetrieveAroundStoresRequest testInstance(double latitude, double longitude, double mapLatitude, double mapLongitude, double distance) {
        return new RetrieveAroundStoresRequest(latitude, longitude, mapLatitude, mapLongitude, distance);
    }

    public Double getDistance() {
        return this.distance / 1000;
    }

}
