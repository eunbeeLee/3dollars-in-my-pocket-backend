package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyStoresWithPaginationResponse {

    private List<StoreInfoResponse> contents = new ArrayList<>();
    private Long totalElements;
    private Long nextCursor;

    private MyStoresWithPaginationResponse(List<StoreInfoResponse> contents, Long totalElements, Long nextCursor) {
        this.contents = contents;
        this.totalElements = totalElements;
        this.nextCursor = nextCursor;
    }

    public static MyStoresWithPaginationResponse newLastPageInstance(List<Store> stores, long totalElements) {
        return of(stores, totalElements, null);
    }

    public static MyStoresWithPaginationResponse of(List<Store> stores, Long totalElements, Long nextCursor) {
        List<StoreInfoResponse> contents = stores.stream()
            .map(StoreInfoResponse::of)
            .collect(Collectors.toList());
        return new MyStoresWithPaginationResponse(contents, totalElements, nextCursor);
    }

}
