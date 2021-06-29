package com.depromeet.threedollar.api.utils;

import com.depromeet.threedollar.domain.utils.LocationDistanceUtils;
import org.junit.jupiter.api.Test;

class LocationDistanceUtilsTest {

	@Test
	void 두_지점간의_거리() {
		// given
		double lat1 = 37.3213727397713;
		double lon1 = 127.076701484353;

		double lat2 = 37.3213727397713;
		double lon2 = 127.076701484353;

		int result = LocationDistanceUtils.getDistance(lat1, lon1, lat2, lon2);

		System.out.println(result);
	}

	@Test
	void 두_지점간의_거리_계산() {

	}

}