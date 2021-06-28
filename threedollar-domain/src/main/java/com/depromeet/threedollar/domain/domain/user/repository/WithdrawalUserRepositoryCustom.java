package com.depromeet.threedollar.domain.domain.user.repository;

import com.depromeet.threedollar.domain.domain.user.WithdrawalUser;

public interface WithdrawalUserRepositoryCustom {

	WithdrawalUser findWithdrawalUserByUserId(Long userId);

}
