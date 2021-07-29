package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDetailResponse extends AuditingTimeResponse {

    private Long reviewId;
    private int rating;
    private String contents;
    private ReviewStatus status;
    private Long storeId;
    private String storeName;
    private UserInfoResponse user;

    @Builder
    private ReviewDetailResponse(Long reviewId, int rating, String contents, ReviewStatus status, Long storeId, String storeName, UserInfoResponse user) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.contents = contents;
        this.status = status;
        this.storeId = storeId;
        this.storeName = storeName;
        this.user = user;
    }

    public static ReviewDetailResponse of(ReviewWithStoreAndCreatorProjection review) {
        ReviewDetailResponse response = ReviewDetailResponse.builder()
            .reviewId(review.getId())
            .rating(review.getRating())
            .contents(review.getContents())
            .status(review.getStatus())
            .storeId(review.getStoreId())
            .storeName(review.getStoreName())
            .user(UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()))
            .build();
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

}
