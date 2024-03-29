package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.application.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.domain.domain.store.StoreImage;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreImageResponse extends AuditingTimeResponse {

    private Long imageId;
    private String url;

    public static StoreImageResponse of(StoreImage storeImage) {
        StoreImageResponse response = new StoreImageResponse(storeImage.getId(), storeImage.getUrl());
        response.setBaseTime(storeImage);
        return response;
    }

    public static StoreImageResponse testInstance(Long imageId, String url) {
        return new StoreImageResponse(imageId, url);
    }

}
