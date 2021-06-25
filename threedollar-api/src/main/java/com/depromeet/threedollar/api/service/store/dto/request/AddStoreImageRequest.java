package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.store.StoreImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddStoreImageRequest {

	private Long storeId;

	private String imageUrl;

	public StoreImage toEntity(Long userId) {
		return StoreImage.newInstance(storeId, userId, imageUrl);
	}

}
