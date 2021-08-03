package com.depromeet.threedollar.api.service.store.dto.request;

import io.micrometer.core.lang.Nullable;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveMyStoresRequest {

    @Min(value = 1, message = "{common.size.min}")
    private int size;

    @Nullable
    private Long cursor;

    /**
     * 총 가게 수를 매번 서버에서 조회하지 않고, 캐싱하기 위한 필드. (Optional)
     */
    @Nullable
    private Long cachingTotalElements;

    @NotNull(message = "{store.latitude.notNull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notNull}")
    private Double longitude;

    public static RetrieveMyStoresRequest testInstance(int size, @Nullable Long lastStoreId, @Nullable Long cachingTotalElements,
                                                       double latitude, double longitude) {
        return new RetrieveMyStoresRequest(size, lastStoreId, cachingTotalElements, latitude, longitude);
    }

}
