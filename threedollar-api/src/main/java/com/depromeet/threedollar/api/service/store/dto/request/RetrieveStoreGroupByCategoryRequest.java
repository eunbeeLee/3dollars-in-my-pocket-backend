package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveStoreGroupByCategoryRequest {

    @NotNull(message = "{store.latitude.notNull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notNull}")
    private Double longitude;

    @NotNull(message = "{store.mapLatitude.notNull}")
    private Double mapLatitude;

    @NotNull(message = "{store.mapLongitude.notNull}")
    private Double mapLongitude;

    @NotNull(message = "{menu.category.notNull}")
    private MenuCategoryType category;

}
