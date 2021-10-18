package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.application.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.domain.domain.review.projection.ReviewWithWriterProjection;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewWithWriterResponse extends AuditingTimeResponse {

    private Long reviewId;
    private int rating;
    private String contents;
    private UserInfoResponse user;

    @Builder(access = AccessLevel.PRIVATE)
    private ReviewWithWriterResponse(Long reviewId, int rating, String contents, UserInfoResponse user) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.contents = contents;
        this.user = user;
    }

    public static ReviewWithWriterResponse of(ReviewWithWriterProjection projection) {
        ReviewWithWriterResponse response = ReviewWithWriterResponse.builder()
            .reviewId(projection.getReviewId())
            .rating(projection.getRating())
            .contents(projection.getContents())
            .user(UserInfoResponse.of(projection.getUserId(), projection.getUserName(), projection.getUserSocialType(), projection.getUserMedalType()))
            .build();
        response.setBaseTime(projection.getCreatedAt(), projection.getUpdatedAt());
        return response;
    }

}
