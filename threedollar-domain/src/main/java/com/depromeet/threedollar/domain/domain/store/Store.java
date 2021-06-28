package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.depromeet.threedollar.domain.domain.common.DayOfTheWeek;
import com.depromeet.threedollar.domain.domain.common.Location;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

	private Float rating;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private final Set<PaymentMethod> paymentMethods = new HashSet<>();

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private final Set<AppearanceDay> appearanceDays = new HashSet<>();

	private boolean isDeleted;

	@Builder
	private Store(Long userId, Double latitude, Double longitude, String storeName, StoreType storeType) {
		this.userId = userId;
		this.location = Location.of(latitude, longitude);
		this.storeName = storeName;
		this.storeType = storeType;
		this.rating = 0f;
		this.isDeleted = false;
	}

	public static Store newInstance(Long userId, Double latitude, Double longitude, String storeName, StoreType storeType) {
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

	private void addAppearanceDay(DayOfTheWeek dayOfTheWeek) {
		AppearanceDay appearanceDay = AppearanceDay.of(this, dayOfTheWeek);
		this.appearanceDays.add(appearanceDay);
	}

	public void addAppearanceDays(Set<DayOfTheWeek> dayOfTheWeeks) {
		for (DayOfTheWeek dayOfTheWeek : dayOfTheWeeks) {
			this.addAppearanceDay(dayOfTheWeek);
		}
	}

	public void update(Double latitude, Double longitude, String storeName, StoreType storeType, Long userId) {
		this.location = Location.of(latitude, longitude);
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

	public void updatePaymentMethods(Set<PaymentMethodType> paymentMethods) {
		this.paymentMethods.clear();
		addPaymentMethods(paymentMethods);
	}

	public void updateAppearanceDays(Set<DayOfTheWeek> dayOfTheWeeks) {
		this.appearanceDays.clear();
		addAppearanceDays(dayOfTheWeeks);
	}

	public void delete() {
		this.isDeleted = true;
	}

}
