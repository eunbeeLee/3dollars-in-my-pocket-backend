package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.api.dto.AudtingTimeResponse;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuResponse extends AudtingTimeResponse {

    private Long menuId;

    private MenuCategoryType category;

    private String name;

    private String price;

    public static MenuResponse of(Menu menu) {
        MenuResponse response = new MenuResponse(menu.getId(), menu.getCategory(), menu.getName(), menu.getPrice());
        response.setBaseTime(menu);
        return response;
    }

}
