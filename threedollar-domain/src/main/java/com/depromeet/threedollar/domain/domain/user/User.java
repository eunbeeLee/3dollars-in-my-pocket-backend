package com.depromeet.threedollar.domain.domain.user;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private SocialInfo socialInfo;

	private String name;

	@Enumerated(EnumType.STRING)
	private UserStatusType status;

	private User(String socialId, UserSocialType socialType, String name) {
		this.socialInfo = SocialInfo.of(socialId, socialType);
		this.name = name;
		this.status = UserStatusType.ACTIVE;
	}

	public static User newInstance(String socialId, UserSocialType socialType, String name) {
		return new User(socialId, socialType, name);
	}

	public UserSocialType getSocialType() {
		return this.socialInfo.getSocialType();
	}

	public void update(String name) {
		this.name = name;
	}

}
