package com.depromeet.threedollar.api.service.auth.dto.request;

import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignOutRequest {

    @NotNull
    private UserSocialType socialType;

}
