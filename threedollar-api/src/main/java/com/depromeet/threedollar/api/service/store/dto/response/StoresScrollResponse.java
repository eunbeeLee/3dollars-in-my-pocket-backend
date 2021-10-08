package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoresScrollResponse {

    private List<StoreInfoResponse> contents = new ArrayList<>();
    private long totalElements;
    private long nextCursor;

    private StoresScrollResponse(List<StoreInfoResponse> contents, long totalElements, long nextCursor) {
        this.contents = contents;
        this.totalElements = totalElements;
        this.nextCursor = nextCursor;
    }

    public static StoresScrollResponse newLastScroll(List<Store> stores, long totalElements, double latitude, double longitude) {
        return of(stores, totalElements, -1L, latitude, longitude);
    }

    public static StoresScrollResponse of(List<Store> stores, long totalElements, long nextCursor, double latitude, double longitude) {
        List<StoreInfoResponse> contents = stores.stream()
            .map(store -> StoreInfoResponse.of(store, latitude, longitude))
            .collect(Collectors.toList());
        return new StoresScrollResponse(contents, totalElements, nextCursor);
    }

}
