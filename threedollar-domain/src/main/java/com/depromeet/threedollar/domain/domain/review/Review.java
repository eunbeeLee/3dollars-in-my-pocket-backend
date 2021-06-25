package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import com.depromeet.threedollar.domain.domain.store.Rating;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long storeId;

	/**
	 * TODO
	 * storeName하고 category가 있는 이유를.. 모르겠음
	 * (마이그레이션 하기 매우 까다로울거 같으니 그냥 일단 가는걸로)
	 */
	private String storeName;

	@Enumerated(value = EnumType.STRING)
	private MenuCategoryType category;

	private String contents;

	@Embedded
	private Rating rating;

	@Enumerated(EnumType.STRING)
	private ReviewStatus status;

}
