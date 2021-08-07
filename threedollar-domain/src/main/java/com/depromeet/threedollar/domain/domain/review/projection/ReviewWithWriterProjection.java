package com.depromeet.threedollar.domain.domain.review.projection;

import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
public class ReviewWithWriterProjection {

    private final Long reviewId;
    private final int rating;
    private final String contents;
    private final ReviewStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final Long storeId;

    private final Long userId;
    private final String userName;
    private final UserSocialType userSocialType;

    @QueryProjection
    public ReviewWithWriterProjection(Long reviewId, int rating, String contents, ReviewStatus status, LocalDateTime createdAt,
                                      LocalDateTime updatedAt, Long storeId, Long userId, String userName,
                                      UserSocialType userSocialType) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.contents = contents;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.storeId = storeId;
        this.userId = userId;
        this.userName = userName;
        this.userSocialType = userSocialType;
    }

}
