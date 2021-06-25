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

	/**
	 * TODO
	 * status, state별로 관리하지 않고
	 * AS-IS
	 * 회원가입 (state = false, name = null, status=ACTIVE) -> 닉네임 설정 (state = true, name = "닉네임", status=ACTIEC) -> 회원탈퇴(status=INACTIVE + WithDrawerUser 추가)
	 * TO-BE
	 * 회원가입 (닉네임 설정까지 완료) -> (name = 닉네임) -> 회원 탈퇴 (WithdrawalUser 컬럼 추가 (백업용)) -> 배치 작업으로 90일 이후 삭제.
	 *
	 * 이 경우 이미 작성한 Store, Review에 대해서 어떻게 보여줄지..
	 */
	@Enumerated(EnumType.STRING)
	private UserStatusType status;

	private boolean state;

}
