package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDetailResponse extends AuditingTimeResponse {

    private Long reviewId;

    private int rating;

    private String contents;

    private ReviewStatus status;

    private Long storeId;

    private String storeName;

    private UserInfoResponse user;

    public static ReviewDetailResponse of(ReviewWithStoreAndCreatorProjection review) {
        ReviewDetailResponse response = new ReviewDetailResponse(review.getId(), review.getRating(), review.getContents(),
            review.getStatus(), review.getStoreId(), review.getStoreName(), UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()));
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

}
