package com.depromeet.threedollar.domain.domain.storedelete;

import com.depromeet.threedollar.domain.domain.storedelete.repository.StoreDeleteRequestRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDeleteRequestRepository extends JpaRepository<StoreDeleteRequest, Long>, StoreDeleteRequestRepositoryCustom {

}
