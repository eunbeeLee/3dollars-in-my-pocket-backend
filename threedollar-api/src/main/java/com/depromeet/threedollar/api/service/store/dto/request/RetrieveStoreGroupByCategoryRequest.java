package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveStoreGroupByCategoryRequest {

    @NotNull(message = "{store.latitude.notnull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notnull}")
    private Double longitude;

    @NotNull(message = "{store.mapLatitude.notnull}")
    private Double mapLatitude;

    @NotNull(message = "{store.mapLongitude.notnull}")
    private Double mapLongitude;

    @NotNull(message = "{menu.category.notnull}")
    private MenuCategoryType categoryType;

}
