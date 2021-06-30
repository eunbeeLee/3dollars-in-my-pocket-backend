package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveStoreDetailInfoRequest {

    @NotNull(message = "{store.storeId.notnull}")
    private Long storeId;

    @NotNull(message = "{store.latitude.notnull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notnull}")
    private Double longitude;

}
