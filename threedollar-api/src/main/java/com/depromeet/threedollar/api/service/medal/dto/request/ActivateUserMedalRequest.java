package com.depromeet.threedollar.api.service.medal.dto.request;

import com.depromeet.threedollar.domain.domain.medal.UserMedalType;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivateUserMedalRequest {

    private UserMedalType medalType;

    public static ActivateUserMedalRequest testInstance(UserMedalType medalType) {
        return new ActivateUserMedalRequest(medalType);
    }

}
