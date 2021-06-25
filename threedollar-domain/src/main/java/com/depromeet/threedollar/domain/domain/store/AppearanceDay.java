package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AppearanceDay extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@Enumerated(value = EnumType.STRING)
	private DayOfTheWeek day;

}

