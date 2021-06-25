package com.depromeet.threedollar.domain.domain.user;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO
 * 마이그레이션 이후에, 삭제된 유저 관리를 다른 방식으로 해야할 것 같음.
 * User.state, status를 다른 방식으로 관리하는 것이 좋아보입니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WithdrawalUser extends AuditingTimeEntity {

	@Id
	private Long userId;

	private String name;

	@Embedded
	private SocialInfo socialInfo;

	private LocalDateTime userCreatedAt;

}
