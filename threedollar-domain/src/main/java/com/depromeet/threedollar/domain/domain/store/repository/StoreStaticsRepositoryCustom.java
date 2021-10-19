package com.depromeet.threedollar.domain.domain.store.repository;

import java.time.LocalDate;

public interface StoreStaticsRepositoryCustom {

    long findActiveStoresCounts();

    long findActiveStoresCountsBetweenDate(LocalDate startDate, LocalDate endDate);

    long findDeletedStoresCountsByDate(LocalDate startDate, LocalDate endDate);

}
