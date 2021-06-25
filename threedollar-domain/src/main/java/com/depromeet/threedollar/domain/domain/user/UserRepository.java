package com.depromeet.threedollar.domain.domain.user;

import com.depromeet.threedollar.domain.domain.user.repository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
