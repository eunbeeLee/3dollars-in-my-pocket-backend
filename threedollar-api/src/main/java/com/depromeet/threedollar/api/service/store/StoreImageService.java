package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.response.StoreImageResponse;
import com.depromeet.threedollar.domain.domain.store.StoreImage;
import com.depromeet.threedollar.domain.domain.store.StoreImageRepository;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreImageService {

	private final StoreRepository storeRepository;
	private final StoreImageRepository storeImageRepository;

	@Transactional
	public StoreImageResponse addStoreImage(Long storeId, String imageUrl, Long userId) {
		StoreServiceUtils.validateExistsStore(storeRepository, storeId);
		return StoreImageResponse.of(storeImageRepository.save(StoreImage.newInstance(storeId, userId, imageUrl)));
	}

	@Transactional
	public void deleteStoreImage(Long storeImageId) {
		StoreImage storeImage = StoreImageServiceUtils.findStoreImageById(storeImageRepository, storeImageId);
		storeImage.delete();
	}

	@Transactional(readOnly = true)
	public List<StoreImageResponse> getStoreImages(Long storeId) {
		List<StoreImage> storeImages = storeImageRepository.findStoreImagesByStoreId(storeId);
		return storeImages.stream()
				.map(StoreImageResponse::of)
				.collect(Collectors.toList());
	}

}
