package com.depromeet.threedollar.domain.domain.menu.projection;

import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MenuStaticsProjection {

    private final MenuCategoryType category;
    private final long counts;

    @QueryProjection
    public MenuStaticsProjection(MenuCategoryType category, long counts) {
        this.category = category;
        this.counts = counts;
    }

}
