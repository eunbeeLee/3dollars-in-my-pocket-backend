package com.depromeet.threedollar.domain.domain.user;

import com.depromeet.threedollar.domain.domain.user.repository.WithdrawalUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalUserRepository extends JpaRepository<WithdrawalUser, Long>, WithdrawalUserRepositoryCustom {

}
