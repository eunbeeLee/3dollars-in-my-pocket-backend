package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.dto.AudtingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithStoreAndCreatorDto;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDetailResponse extends AudtingTimeResponse {

    private Long reviewId;

    private int rating;

    private ReviewStatus status;

    private Long storeId;

    private UserInfoResponse user;

    public static ReviewDetailResponse of(ReviewWithStoreAndCreatorDto review) {
        ReviewDetailResponse response = new ReviewDetailResponse(review.getId(), review.getRating(), review.getStatus(),
            review.getStoreId(), UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()));
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

}
