package com.depromeet.threedollar.domain.domain.menu.repository;

import com.depromeet.threedollar.domain.domain.menu.Menu;

import java.util.List;

public interface MenuRepositoryCustom {

	List<Menu> findAllByStoreId(Long storeId);

}
