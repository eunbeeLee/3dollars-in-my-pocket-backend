package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDetailInfoResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import com.depromeet.threedollar.api.service.user.UserServiceUtils;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// TODO 전반적 개선 필요.
@RequiredArgsConstructor
@Service
public class StoreRetrieveService {

	private static final double LIMIT_DISTANCE = 2.0;

	private final StoreRepository storeRepository;
	private final StoreImageService storeImageService;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public List<StoreInfoResponse> getAllStoresLessThanDistance(RetrieveAroundStoresRequest request) {
		double distance = request.getDistance() > LIMIT_DISTANCE ? LIMIT_DISTANCE : request.getDistance();
		List<Store> stores = storeRepository.findStoresByLocationLessThanDistance(request.getMapLatitude(), request.getMapLongitude(), distance);
		return stores.stream()
				.map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public StoreDetailInfoResponse getDetailStoreInfo(Long storeId, Double latitude, Double longitude) {
		Store store = StoreServiceUtils.findStoreById(storeRepository, storeId);
		return StoreDetailInfoResponse.of(store, storeImageService.getStoreImages(storeId), latitude, longitude, UserServiceUtils.findUserById(userRepository, store.getUserId()));
	}

}
