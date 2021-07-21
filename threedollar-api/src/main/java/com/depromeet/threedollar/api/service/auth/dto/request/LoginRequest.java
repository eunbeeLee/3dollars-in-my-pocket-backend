package com.depromeet.threedollar.api.service.auth.dto.request;

import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotBlank
    private String token;

    @NotNull
    private UserSocialType socialType;

    public static LoginRequest testInstance(String token, UserSocialType socialType) {
        return new LoginRequest(token, socialType);
    }

}
