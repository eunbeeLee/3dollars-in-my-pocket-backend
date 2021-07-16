package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreatorDto;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse extends AuditingTimeResponse {

    private Long reviewId;

    private int rating;

    private String contents;

    private UserInfoResponse user;

    public static ReviewResponse of(ReviewWithCreatorDto review) {
        ReviewResponse response = new ReviewResponse(review.getId(), review.getRating(), review.getContents(), UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()));
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

}
