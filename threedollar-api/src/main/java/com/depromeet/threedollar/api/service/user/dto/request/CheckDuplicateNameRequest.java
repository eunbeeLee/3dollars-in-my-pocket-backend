package com.depromeet.threedollar.api.service.user.dto.request;

import com.depromeet.threedollar.api.config.validator.NickName;
import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckDuplicateNameRequest {

    @NotBlank(message = "{user.name.notBlank}")
    @NickName
    private String name;

    public static CheckDuplicateNameRequest testInstance(String name) {
        return new CheckDuplicateNameRequest(name);
    }

}
