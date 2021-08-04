package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDetailWithPaginationResponse {

    private List<ReviewDetailResponse> contents = new ArrayList<>();
    private long totalElements;
    private Long nextCursor;

    private ReviewDetailWithPaginationResponse(List<ReviewDetailResponse> contents, long totalElements, Long nextCursor) {
        this.contents = contents;
        this.totalElements = totalElements;
        this.nextCursor = nextCursor;
    }

    public static ReviewDetailWithPaginationResponse of(List<ReviewWithStoreAndCreatorProjection> reviews,
                                                        long totalElements, Map<Long, Store> cachedStores, Long nextCursor) {
        List<ReviewDetailResponse> contents = reviews.stream()
            .map(review -> ReviewDetailResponse.of(review, cachedStores.get(review.getStoreId())))
            .collect(Collectors.toList());
        return new ReviewDetailWithPaginationResponse(contents, totalElements, nextCursor);
    }

    public static ReviewDetailWithPaginationResponse newLastScroll(List<ReviewWithStoreAndCreatorProjection> reviews,
                                                                   long totalElements, Map<Long, Store> cachedStores) {
        return of(reviews, totalElements, cachedStores, null);
    }

}
