package com.depromeet.threedollar.domain.domain.menu;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.depromeet.threedollar.domain.domain.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@Column(length = 50)
	private String name;

	@Column(length = 100)
	private String price;

	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private MenuCategoryType category;

	private Menu(Store store, String name, String price, MenuCategoryType category) {
		this.store = store;
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public static Menu of(Store store, String name, String price, MenuCategoryType category) {
		return new Menu(store, name, price, category);
	}

}
