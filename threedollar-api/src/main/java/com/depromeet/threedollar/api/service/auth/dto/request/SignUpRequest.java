package com.depromeet.threedollar.api.service.auth.dto.request;

import com.depromeet.threedollar.api.service.user.dto.request.CreateUserRequest;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

    @NotBlank
    private String token;

    @NotBlank
    private String name;

    @NotNull
    private UserSocialType socialType;

    public static SignUpRequest testInstance(String token, String name, UserSocialType socialType) {
        return new SignUpRequest(token, name, socialType);
    }

    public CreateUserRequest toCreateUserRequest(String socialId) {
        return CreateUserRequest.of(socialId, socialType, name);
    }

}
