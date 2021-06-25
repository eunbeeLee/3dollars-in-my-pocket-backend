package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.domain.domain.store.StoreImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreImageResponse {

	private Long id;

	private String url;

	public static StoreImageResponse of(StoreImage storeImage) {
		return new StoreImageResponse(storeImage.getId(), storeImage.getUrl());
	}

}
