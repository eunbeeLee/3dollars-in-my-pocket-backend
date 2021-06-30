package com.depromeet.threedollar.api.service.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckDuplicateNameRequest {

    @NotBlank(message = "{user.name.notBlank}")
    private String name;

    public static CheckDuplicateNameRequest testInstance(String name) {
        return new CheckDuplicateNameRequest(name);
    }

}
