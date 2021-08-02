package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.review.dto.response.ReviewResponse;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveAroundStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveMyStoresRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreGroupByCategoryRequest;
import com.depromeet.threedollar.api.service.store.dto.request.RetrieveStoreDetailInfoRequest;
import com.depromeet.threedollar.api.service.store.dto.response.*;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreRetrieveService {

    private static final double LIMIT_DISTANCE = 2.0;

    private final StoreImageService storeImageService;

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<StoreInfoResponse> getNearStores(RetrieveAroundStoresRequest request) {
        List<Store> stores = storeRepository.findStoresByLocationLessThanDistance(request.getMapLatitude(), request.getMapLongitude(), Math.min(request.getDistance(), LIMIT_DISTANCE));
        return stores.stream()
            .map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
            .sorted(Comparator.comparing(StoreInfoResponse::getDistance))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StoreDetailInfoResponse getDetailStoreInfo(RetrieveStoreDetailInfoRequest request) {
        Store store = StoreServiceUtils.findStoreByIdFetchJoinMenu(storeRepository, request.getStoreId());
        User creator = userRepository.findUserById(store.getUserId());
        return StoreDetailInfoResponse.of(store, storeImageService.getStoreImages(request.getStoreId()), request.getLatitude(),
            request.getLongitude(), creator, getStoreReviewsResponse(request.getStoreId()));
    }

    private List<ReviewResponse> getStoreReviewsResponse(Long storeId) {
        return reviewRepository.findAllWithCreatorByStoreId(storeId).stream()
            .map(ReviewResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MyStoresWithPaginationResponse retrieveMyStores(RetrieveMyStoresRequest request, Long userId) {
        Page<Store> stores = storeRepository.findAllByUserIdWithPagination(userId, PageRequest.of(request.getPage(), request.getSize()));
        return MyStoresWithPaginationResponse.of(stores, request.getLatitude(), request.getLongitude());
    }

    @Transactional(readOnly = true)
    public StoresGroupByDistanceResponse retrieveStoresGroupByDistance(RetrieveStoreGroupByCategoryRequest request) {
        List<StoreInfoResponse> stores = findNearByStores(request.getMapLatitude(), request.getMapLongitude()).stream()
            .filter(store -> store.getMenuCategories().contains(request.getCategory()))
            .map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
            .sorted(Comparator.comparing(StoreInfoResponse::getDistance))
            .collect(Collectors.toList());
        return StoresGroupByDistanceResponse.of(stores);
    }

    @Transactional(readOnly = true)
    public StoresGroupByReviewResponse retrieveStoresGroupByRating(RetrieveStoreGroupByCategoryRequest request) {
        List<StoreInfoResponse> stores = findNearByStores(request.getMapLatitude(), request.getMapLongitude()).stream()
            .filter(store -> store.getMenuCategories().contains(request.getCategory()))
            .map(store -> StoreInfoResponse.of(store, request.getLatitude(), request.getLongitude()))
            .sorted(Comparator.comparing(StoreInfoResponse::getRating).reversed())
            .collect(Collectors.toList());
        return StoresGroupByReviewResponse.of(stores);
    }

    private List<Store> findNearByStores(double latitude, double longitude) {
        return storeRepository.findStoresByLocationLessThanDistance(latitude, longitude, 2.0);
    }

}
