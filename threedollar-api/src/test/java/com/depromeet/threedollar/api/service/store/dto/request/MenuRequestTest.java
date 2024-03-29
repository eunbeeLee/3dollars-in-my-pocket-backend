package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuRequestTest {

    @Test
    void 같은_메뉴_값을_가지면_동등성_비교시_true_반환() {
        // given
        String name = "메뉴";
        String price = "가격";
        MenuCategoryType type = MenuCategoryType.BUNGEOPPANG;

        MenuRequest source = MenuRequest.of(name, price, type);
        MenuRequest target = MenuRequest.of(name, price, type);

        // when & then
        assertThat(source).isEqualTo(target);
    }

    @Test
    void 메뉴_하나라도_다른_값을_가지면_동등성_비교시_false() {
        // given
        String name = "메뉴";
        MenuCategoryType type = MenuCategoryType.BUNGEOPPANG;

        MenuRequest source = MenuRequest.of(name, "가격1", type);
        MenuRequest target = MenuRequest.of(name, "가격2", type);

        // when & then
        assertThat(source).isNotEqualTo(target);
    }

}
