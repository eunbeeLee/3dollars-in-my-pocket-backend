package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.review.dto.response.ReviewResponse;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveMyStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreGroupByCategoryRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.*;
import com.depromeet.threedollar.api.service.user.UserServiceUtils;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// TODO 전반적 개선 필요.
@RequiredArgsConstructor
@Service
public class StoreRetrieveService {

	private static final double LIMIT_DISTANCE = 2.0;

	private final StoreImageService storeImageService;

	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public List<StoreInfoResponse> getAllStoresLessThanDistance(RetrieveAroundStoresRequest request) {
		double distance = request.getDistance() > LIMIT_DISTANCE ? LIMIT_DISTANCE : request.getDistance();
		List<Store> stores = storeRepository.findStoresByLocationLessThanDistance(request.getMapLatitude(), request.getMapLongitude(), distance);
		return stores.stream()
				.map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public StoreDetailInfoResponse getDetailStoreInfo(RetrieveStoreInfoRequest request) {
		Store store = StoreServiceUtils.findStoreById(storeRepository, request.getStoreId());
		return StoreDetailInfoResponse.of(store, storeImageService.getStoreImages(request.getStoreId()), request.getLatitude(), request.getLongitude(),
				UserServiceUtils.findUserById(userRepository, store.getUserId()), getReviewResponse(request.getStoreId()));
	}

	private List<ReviewResponse> getReviewResponse(Long storeId) {
		return reviewRepository.findAllReviewWithCreatorByStoreId(storeId).stream()
				.map(ReviewResponse::of)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public MyStoresWithPaginationResponse retrieveMyStores(RetrieveMyStoresRequest request, Long userId) {
		Page<Store> stores = storeRepository.findAllStoresByUserIdWithPagination(userId, PageRequest.of(request.getPage(), request.getSize()));
		return MyStoresWithPaginationResponse.of(stores, request.getLatitude(), request.getLongitude());
	}

	@Transactional(readOnly = true)
	public StoresGroupByDistanceResponse retrieveStoresGroupByDistance(RetrieveStoreGroupByCategoryRequest request) {
		List<StoreInfoResponse> stores = storeRepository.findStoresByLocationLessThanDistance(
				request.getMapLatitude(), request.getMapLongitude(), 2.0).stream()
				.filter(store -> store.getMenuCategories().contains(request.getCategoryType()))
				.map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
				.sorted(Comparator.comparing(StoreInfoResponse::getDistance))
				.collect(Collectors.toList());
		return StoresGroupByDistanceResponse.of(stores);
	}

	@Transactional(readOnly = true)
	public StoresGroupByReviewResponse retrieveStoresGroupByRating(RetrieveStoreGroupByCategoryRequest request) {
		List<StoreInfoResponse> stores = storeRepository.findStoresByLocationLessThanDistance(
				request.getMapLatitude(), request.getMapLongitude(), 2.0).stream()
				.filter(store -> store.getMenuCategories().contains(request.getCategoryType()))
				.map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
				.sorted(Comparator.comparing(StoreInfoResponse::getRating).reversed())
				.collect(Collectors.toList());
		return StoresGroupByReviewResponse.of(stores);
	}

}
