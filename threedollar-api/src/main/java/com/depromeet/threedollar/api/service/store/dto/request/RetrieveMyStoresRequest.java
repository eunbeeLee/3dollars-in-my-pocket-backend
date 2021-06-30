package com.depromeet.threedollar.api.service.store.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveMyStoresRequest {

    @Min(value = 1, message = "{common.page.min}")
    private int size;

    @Min(value = 0, message = "{common.size.min}")
    private int page;

    @NotNull(message = "{store.latitude.notnull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notnull}")
    private Double longitude;

}
