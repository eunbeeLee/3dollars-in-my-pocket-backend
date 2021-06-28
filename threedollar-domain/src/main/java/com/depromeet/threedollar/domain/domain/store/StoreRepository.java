package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.store.repository.StoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
