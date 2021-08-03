package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfoResponse extends AuditingTimeResponse {

    private Long reviewId;
    private Long storeId;
    private String content;
    private int rating;
    private ReviewStatus status;

    @Builder
    private ReviewInfoResponse(Long reviewId, Long storeId, String content, int rating, ReviewStatus status) {
        this.reviewId = reviewId;
        this.storeId = storeId;
        this.content = content;
        this.rating = rating;
        this.status = status;
    }

    public static ReviewInfoResponse of(Review review) {
        ReviewInfoResponse response = ReviewInfoResponse.builder()
            .reviewId(review.getId())
            .storeId(review.getStoreId())
            .content(review.getContents())
            .rating(review.getRating())
            .status(review.getStatus())
            .build();
        response.setBaseTime(review);
        return response;
    }

}
