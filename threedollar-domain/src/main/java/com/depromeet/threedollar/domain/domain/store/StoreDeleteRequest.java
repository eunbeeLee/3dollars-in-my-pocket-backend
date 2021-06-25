package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
// TODO 테이블 이름 delete_request -> store_delete_request 마이그레이션 필요
public class StoreDeleteRequest extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long storeId;

	private Long userId;

	@Enumerated(value = EnumType.STRING)
	private DeleteReasonType reason;

}
