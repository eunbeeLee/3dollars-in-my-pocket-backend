package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithWriterProjection;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewWithWriterResponse extends AuditingTimeResponse {

    private Long reviewId;
    private int rating;
    private String contents;
    private UserInfoResponse user;

    @Builder
    private ReviewWithWriterResponse(Long reviewId, int rating, String contents, UserInfoResponse user) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.contents = contents;
        this.user = user;
    }

    public static ReviewWithWriterResponse of(ReviewWithWriterProjection review) {
        ReviewWithWriterResponse response = ReviewWithWriterResponse.builder()
            .reviewId(review.getId())
            .rating(review.getRating())
            .contents(review.getContents())
            .user(UserInfoResponse.of(review.getUserId(), review.getUserName(), review.getUserSocialType()))
            .build();
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

}
