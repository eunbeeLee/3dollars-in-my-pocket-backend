package com.depromeet.threedollar.domain.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SocialInfo {

	private String socialId;

	@Enumerated(EnumType.STRING)
	private UserProviderType socialType;

}
