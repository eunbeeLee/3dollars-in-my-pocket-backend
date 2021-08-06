package com.depromeet.threedollar.domain.domain.review.projection;

import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
public class ReviewWithWriterProjection {

    private final Long id;
    private final int rating;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final Long userId;
    private final String userName;
    private final UserSocialType userSocialType;

    @QueryProjection
    public ReviewWithWriterProjection(Long id, int rating, String contents, LocalDateTime createdAt,
                                      LocalDateTime updatedAt, Long userId, String userName, UserSocialType userSocialType) {
        this.id = id;
        this.rating = rating;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.userName = userName;
        this.userSocialType = userSocialType;
    }

}
