package com.depromeet.threedollar.api.service.user.dto.response;

import com.depromeet.threedollar.api.dto.AudtingTimeResponse;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse extends AudtingTimeResponse {

    private Long userId;

    private String name;

    private UserSocialType socialType;

    public static UserInfoResponse of(User user) {
        UserInfoResponse response = new UserInfoResponse(user.getId(), user.getName(), user.getSocialType());
        response.setBaseTime(user);
        return response;
    }

    public static UserInfoResponse of(Long userId, String userName, UserSocialType userSocialType) {
        return new UserInfoResponse(userId, userName, userSocialType);
    }

}
