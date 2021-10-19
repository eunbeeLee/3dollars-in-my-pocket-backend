package com.depromeet.threedollar.domain.domain.user.repository;

import java.time.LocalDate;

public interface UserStaticsRepositoryCustom {

    long findUsersCount();

    long findUsersCountBetweenDate(LocalDate startDate, LocalDate endDate);

}
