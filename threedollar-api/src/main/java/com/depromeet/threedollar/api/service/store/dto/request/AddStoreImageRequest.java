package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.store.StoreImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddStoreImageRequest {

	@NotNull
	private Long storeId;

	@NotBlank
	private String imageUrl;

	public StoreImage toEntity(Long userId) {
		return StoreImage.newInstance(storeId, userId, imageUrl);
	}

}
