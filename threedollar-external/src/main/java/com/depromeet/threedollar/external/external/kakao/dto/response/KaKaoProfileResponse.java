package com.depromeet.threedollar.external.external.kakao.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KaKaoProfileResponse {

    private String id;

    private KaKaoProfileResponse(String id) {
        this.id = id;
    }

    public static KaKaoProfileResponse testInstance(String socialId) {
        return new KaKaoProfileResponse(socialId);
    }

}
