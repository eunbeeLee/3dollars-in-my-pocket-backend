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
    private Long nextCursor;

    private StoresScrollResponse(List<StoreInfoResponse> contents, Long totalElements, Long nextCursor) {
        this.contents = contents;
        this.totalElements = totalElements;
        this.nextCursor = nextCursor;
    }

    public static StoresScrollResponse newLastScroll(List<Store> stores, long totalElements) {
        return of(stores, totalElements, null);
    }

    public static StoresScrollResponse of(List<Store> stores, Long totalElements, Long nextCursor) {
        List<StoreInfoResponse> contents = stores.stream()
            .map(StoreInfoResponse::of)
            .collect(Collectors.toList());
        return new StoresScrollResponse(contents, totalElements, nextCursor);
    }

}
