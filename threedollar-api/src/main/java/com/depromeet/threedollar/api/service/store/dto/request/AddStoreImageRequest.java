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

	@NotNull(message = "{store.storeId.notnull}")
	private Long storeId;

	@NotBlank(message = "{store.imageUrl.notBlank}")
	private String imageUrl;

	public StoreImage toEntity(Long userId) {
		return StoreImage.newInstance(storeId, userId, imageUrl);
	}

}
