package com.depromeet.threedollar.api.service.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUserInfoRequest {

    @NotBlank(message = "{member.name.notBlank}")
    private String nickName;

    public static UpdateUserInfoRequest testInstance(String name) {
        return new UpdateUserInfoRequest(name);
    }

}
