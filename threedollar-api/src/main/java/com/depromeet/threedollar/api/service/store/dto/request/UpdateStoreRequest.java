package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.store.PaymentMethodType;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStoreRequest {

    @NotNull(message = "{store.latitude.notNull}")
    private Double latitude;

    @NotNull(message = "{store.longitude.notNull}")
    private Double longitude;

    @NotBlank(message = "{store.name.notBlank}")
    private String storeName;

    @NotNull(message = "{store.type.notNull}")
    private StoreType storeType;

    @NotNull(message = "{store.appearanceDays.notNull}")
    private Set<DayOfTheWeek> appearanceDays = new HashSet<>();

    @NotNull(message = "{store.paymentMethods.notNull}")
    private Set<PaymentMethodType> paymentMethods = new HashSet<>();

    @JsonProperty("menu")
    @NotEmpty(message = "{store.menu.notEmpty}")
    @Valid
    private List<MenuRequest> menus = new ArrayList<>();

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateStoreRequest(Double latitude, Double longitude, String storeName, StoreType storeType,
                              Set<DayOfTheWeek> appearanceDays, Set<PaymentMethodType> paymentMethods, List<MenuRequest> menus) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.storeType = storeType;
        this.appearanceDays = appearanceDays;
        this.paymentMethods = paymentMethods;
        this.menus = menus;
    }

    public List<Menu> toMenus(Store store) {
        return menus.stream()
            .map(menu -> menu.toEntity(store))
            .collect(Collectors.toList());
    }

}
