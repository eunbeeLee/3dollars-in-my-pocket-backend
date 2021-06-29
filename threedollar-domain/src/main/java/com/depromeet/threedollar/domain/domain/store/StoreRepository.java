package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.store.repository.StoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 현재 풀테이블 스캔을 통해서 검색하고 있는데 (B-Tree의 공간정보 인덱스 한계로 인해서)
 * R-Tree 인덱스를 이용하게 바꾸도록 개선.
 */
public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

    @Query(value = "SELECT *, (" +
        "    6371 * acos (" +
        "      cos ( radians( :latitude ) )  " +
        "      * cos( radians( latitude ) )" +
        "      * cos( radians( longitude ) - radians( :longitude ) )" +
        "      + sin ( radians( :latitude ) )" +
        "      * sin( radians( latitude ) )" +
        "    )" +
        "  ) AS distance" +
        "  FROM store" +
        "  GROUP BY id" +
        "  HAVING distance < :distance" +
        "  ORDER BY distance", nativeQuery = true)
    List<Store> findStoresByLocationLessThanDistance(@Param("latitude") final Double latitude,
                                                     @Param("longitude") final Double longitude,
                                                     @Param("distance") final Double distance);

}
