package com.depromeet.threedollar.domain.domain.menu;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
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

	private Long storeId;

	private String name;

	private String price;

	@Enumerated(EnumType.STRING)
	private MenuCategoryType category;

	private Menu(Long storeId, String name, String price, MenuCategoryType category) {
		this.storeId = storeId;
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public static Menu newInstance(Long storeId, String name, String price, MenuCategoryType category) {
		return new Menu(storeId, name, price, category);
	}

}
