package com.depromeet.threedollar.external.external.auth.kakao.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KaKaoProfileResponse {

	private String id;

	private KaKaoProfileResponse(String id) {
		this.id = id;
	}

	public static KaKaoProfileResponse testInstance(String socialId) {
		return new KaKaoProfileResponse(socialId);
	}

}
