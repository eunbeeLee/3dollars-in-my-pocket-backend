package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.depromeet.threedollar.domain.domain.common.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	@Embedded
	private Location location;

	private String storeName;

	@Enumerated(EnumType.STRING)
	private StoreType storeType;

	@Embedded
	private Rating rating;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<StoreImage> images = new ArrayList<>();

}
