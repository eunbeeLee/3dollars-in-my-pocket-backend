package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveStoreDetailInfoRequest {

    @NotNull(message = "{store.storeId.notNull}")
    private Long storeId;

    @NotNull(message = "{store.latitude.notNull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notNull}")
    private Double longitude;

    public static RetrieveStoreDetailInfoRequest testInstance(Long storeId, double latitude, double longitude) {
        return new RetrieveStoreDetailInfoRequest(storeId, latitude, longitude);
    }

}
