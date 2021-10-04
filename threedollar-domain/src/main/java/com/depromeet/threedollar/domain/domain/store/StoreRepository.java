package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.store.repository.StoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

}
