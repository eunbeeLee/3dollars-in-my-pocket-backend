package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoreImage extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "store_id", nullable = false)
	private Long storeId;

	private Long userId;

	private String url;

	private boolean isDeleted;

	private StoreImage(Long storeId, Long userId, String url) {
		this.storeId = storeId;
		this.userId = userId;
		this.url = url;
		this.isDeleted = false;
	}

	public static StoreImage newInstance(Long storeId, Long userId, String imageUrl) {
		return new StoreImage(storeId, userId, imageUrl);
	}

	public void delete() {
		this.isDeleted = true;
	}

}
