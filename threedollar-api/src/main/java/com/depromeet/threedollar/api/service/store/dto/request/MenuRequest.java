package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequest {

    @NotBlank(message = "{menu.name.notBlank}")
    private String name;

    @NotBlank(message = "{menu.price.notBlank}")
    private String price;

    @NotNull(message = "{menu.category.notnull}")
    private MenuCategoryType category;

    public static MenuRequest of(String name, String price, MenuCategoryType category) {
        return new MenuRequest(name, price, category);
    }

    public Menu toEntity(Store store) {
        return Menu.of(store, name, price, category);
    }

}
