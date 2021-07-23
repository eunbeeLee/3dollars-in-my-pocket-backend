package com.depromeet.threedollar.domain.domain.review.repository.projection;

import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewWithCreatorProjection {

    private Long id;
    private int rating;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId;
    private String userName;
    private UserSocialType userSocialType;

}
