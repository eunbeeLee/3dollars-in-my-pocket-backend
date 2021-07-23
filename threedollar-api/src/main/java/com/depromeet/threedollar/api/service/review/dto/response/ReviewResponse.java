package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.api.common.dto.AuditingTimeResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithCreatorProjection;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
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

    public static ReviewResponse of(ReviewWithCreatorProjection review) {
        ReviewResponse response = new ReviewResponse(review.getId(), review.getRating(), review.getContents(),
            getUserInfoResponse(review.getUserId(), review.getUserName(), review.getUserSocialType()));
        response.setBaseTime(review.getCreatedAt(), review.getUpdatedAt());
        return response;
    }

    private static UserInfoResponse getUserInfoResponse(Long userId, String userName, UserSocialType socialType) {
        if (userId == null) {
            // 회원탈퇴한 유저인경우.
            return UserInfoResponse.of(User.deletedUser());
        }
        return UserInfoResponse.of(userId, userName, socialType);
    }

}
