package com.depromeet.threedollar.api.service.auth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

	@NotBlank
	private String token;

	@NotBlank
	private String name;

}
