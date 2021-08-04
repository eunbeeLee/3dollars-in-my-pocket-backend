package com.depromeet.threedollar.api.service.review.dto.request;

import lombok.*;

import javax.validation.constraints.Min;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveMyReviewsRequest {

    @Min(value = 1, message = "{common.size.min}")
    private int size;

    private Long cursor; // 총 리뷰 수를 매번 서버에서 조회하지 않고, 캐싱하기 위한 필드. (Optional)

    private Long cachingTotalElements;

    public static RetrieveMyReviewsRequest testInstance(int size, Long cursor, Long cachingTotalElements) {
        return new RetrieveMyReviewsRequest(size, cursor, cachingTotalElements);
    }

}
