package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveNearStoresRequest {

    @NotNull(message = "{store.latitude.notNull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notNull}")
    private Double longitude;

    @NotNull(message = "{store.mapLatitude.notNull}")
    private Double mapLatitude;

    @NotNull(message = "{store.mapLongitude.notNull}")
    private Double mapLongitude;

    @NotNull(message = "{store.distance.notNull}")
    private Double distance;

    public static RetrieveNearStoresRequest testInstance(double latitude, double longitude, double mapLatitude, double mapLongitude, double distance) {
        return new RetrieveNearStoresRequest(latitude, longitude, mapLatitude, mapLongitude, distance);
    }

    public Double getDistance() {
        return this.distance / 1000;
    }

}
