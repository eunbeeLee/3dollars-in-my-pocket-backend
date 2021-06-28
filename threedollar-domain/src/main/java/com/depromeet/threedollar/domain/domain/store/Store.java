package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.common.Location;
import com.depromeet.threedollar.domain.domain.menu.Menu;
import com.depromeet.threedollar.domain.domain.menu.MenuCategoryType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Embedded
	private Location location;

	@Column(nullable = false, length = 300)
	private String storeName;

	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private StoreType storeType;

	@Column(nullable = false)
	private double rating;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private final Set<PaymentMethod> paymentMethods = new HashSet<>();

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private final Set<AppearanceDay> appearanceDays = new HashSet<>();

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Menu> menus = new ArrayList<>();

	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private StoreStatus status;

	@Builder
	private Store(Long userId, double latitude, double longitude, String storeName, StoreType storeType) {
		this.userId = userId;
		this.location = Location.of(latitude, longitude);
		this.storeName = storeName;
		this.storeType = storeType;
		this.rating = 0.0;
		this.status = StoreStatus.ACTIVE;
	}

	public static Store newInstance(Long userId, double latitude, double longitude, String storeName, StoreType storeType) {
		return new Store(userId, latitude, longitude, storeName, storeType);
	}

	private void addPaymentMethod(PaymentMethodType type) {
		PaymentMethod paymentMethod = PaymentMethod.of(this, type);
		this.paymentMethods.add(paymentMethod);
	}

	public void addPaymentMethods(Set<PaymentMethodType> types) {
		for (PaymentMethodType type : types) {
			this.addPaymentMethod(type);
		}
	}

	public void updatePaymentMethods(Set<PaymentMethodType> paymentMethods) {
		this.paymentMethods.clear();
		addPaymentMethods(paymentMethods);
	}

	private void addAppearanceDay(DayOfTheWeek dayOfTheWeek) {
		AppearanceDay appearanceDay = AppearanceDay.of(this, dayOfTheWeek);
		this.appearanceDays.add(appearanceDay);
	}

	public void addAppearanceDays(Set<DayOfTheWeek> dayOfTheWeeks) {
		for (DayOfTheWeek dayOfTheWeek : dayOfTheWeeks) {
			this.addAppearanceDay(dayOfTheWeek);
		}
	}

	public void updateAppearanceDays(Set<DayOfTheWeek> dayOfTheWeeks) {
		this.appearanceDays.clear();
		addAppearanceDays(dayOfTheWeeks);
	}

	private void addMenu(Menu menu) {
		this.menus.add(menu);
	}

	public void addMenus(List<Menu> menus) {
		for (Menu menu : menus) {
			this.addMenu(menu);
		}
	}

	public void updateMenu(List<Menu> menus) {
		this.menus.clear();
		addMenus(menus);
	}

	public void updateLocation(Double latitude, Double longitude) {
		this.location = Location.of(latitude, longitude);
	}

	public void updateInfo(String storeName, StoreType storeType, Long userId) {
		this.storeName = storeName;
		this.storeType = storeType;
		this.userId = userId;
	}

	public Double getLatitude() {
		return this.location.getLatitude();
	}

	public Double getLongitude() {
		return this.location.getLongitude();
	}

	public void delete() {
		this.status = StoreStatus.DELETED;
	}

	public List<MenuCategoryType> getMenuCategories() {
		return this.menus.stream()
				.map(Menu::getCategory).distinct()
				.collect(Collectors.toList());
	}

	public Set<DayOfTheWeek> getAppearanceDaysType() {
		return this.appearanceDays.stream()
				.map(AppearanceDay::getDay)
				.collect(Collectors.toSet());
	}

	public Set<PaymentMethodType> getPaymentMethodsType() {
		return this.paymentMethods.stream()
				.map(PaymentMethod::getMethod)
				.collect(Collectors.toSet());
	}

	public void updateRating(double average) {
		this.rating = average;
	}

}
