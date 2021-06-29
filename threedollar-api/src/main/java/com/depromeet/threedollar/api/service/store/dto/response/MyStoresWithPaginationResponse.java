package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyStoresWithPaginationResponse {

	private List<StoreInfoResponse> content = new ArrayList<>();
	private long totalElements;
	private long totalPages;

	private MyStoresWithPaginationResponse(List<StoreInfoResponse> content, long totalElements, long totalPages) {
		this.content = content;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

	public static MyStoresWithPaginationResponse of(Page<Store> stores, double latitude, double longitude) {
		List<StoreInfoResponse> responses = stores.getContent().stream()
				.map(store -> StoreInfoResponse.of(store, latitude, longitude))
				.collect(Collectors.toList());
		return new MyStoresWithPaginationResponse(responses, stores.getTotalElements(), stores.getTotalPages());
	}

}
