package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.store.repository.StoreImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long>, StoreImageRepositoryCustom {

}
