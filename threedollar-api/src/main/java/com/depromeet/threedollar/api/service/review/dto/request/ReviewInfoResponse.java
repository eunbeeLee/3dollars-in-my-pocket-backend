package com.depromeet.threedollar.api.service.review.dto.request;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfoResponse extends AuditingTimeResponse {

    private Long reviewId;

    private Long storeId;

    private String content;

    private int rating;

    private ReviewStatus status;

    public static ReviewInfoResponse of(Review review) {
        ReviewInfoResponse response = new ReviewInfoResponse(review.getId(), review.getStoreId(), review.getContents(), review.getRating(), review.getStatus());
        response.setBaseTime(review);
        return response;
    }

}
