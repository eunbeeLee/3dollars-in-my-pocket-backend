package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithCreatorProjection;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse extends AuditingTimeResponse {

    private Long reviewId;
    private int rating;
    private String contents;
    private UserInfoResponse user;

    @Builder
    private ReviewResponse(Long reviewId, int rating, String contents, UserInfoResponse user) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.contents = contents;
        this.user = user;
    }

    public static ReviewResponse of(ReviewWithCreatorProjection review) {
        ReviewResponse response = ReviewResponse.builder()
            .reviewId(review.getId())
            .rating(review.getRating())
            .contents(review.getContents())
            .user(UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()))
            .build();
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

}
