package com.depromeet.threedollar.domain.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SocialInfo {

	private String socialId;

	@Enumerated(EnumType.STRING)
	private UserSocialType socialType;

	private SocialInfo(String socialId, UserSocialType socialType) {
		this.socialId = socialId;
		this.socialType = socialType;
	}

	public static SocialInfo of(String socialId, UserSocialType socialType) {
		return new SocialInfo(socialId, socialType);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SocialInfo that = (SocialInfo) o;
		return Objects.equals(socialId, that.socialId) && socialType == that.socialType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(socialId, socialType);
	}

}
