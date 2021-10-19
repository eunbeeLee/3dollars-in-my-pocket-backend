package com.depromeet.threedollar.domain.domain.menu.repository;

import com.depromeet.threedollar.domain.domain.menu.repository.projection.MenuStaticsProjection;

import java.util.List;

public interface MenuStaticsRepositoryCustom {

    List<MenuStaticsProjection> countsGroupByMenu();

}
