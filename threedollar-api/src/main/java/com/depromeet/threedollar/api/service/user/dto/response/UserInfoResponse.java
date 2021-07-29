package com.depromeet.threedollar.api.service.user.dto.response;

import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private Long userId;

    private String name;

    private UserSocialType socialType;

    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(user.getId(), user.getName(), user.getSocialType());
    }

    public static UserInfoResponse of(Long userId, String userName, UserSocialType userSocialType) {
        return new UserInfoResponse(userId, userName, userSocialType);
    }

}
