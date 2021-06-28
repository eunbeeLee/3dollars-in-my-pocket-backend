package com.depromeet.threedollar.domain.domain.storedelete;

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

	private StoreDeleteRequest(Long storeId, Long userId, DeleteReasonType reason) {
		this.storeId = storeId;
		this.userId = userId;
		this.reason = reason;
	}

	public static StoreDeleteRequest of(Long storeId, Long userId, DeleteReasonType reason) {
		return new StoreDeleteRequest(storeId, userId, reason);
	}

}
