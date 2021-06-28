package com.depromeet.threedollar.domain.domain.store;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PaymentMethod extends AuditingTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@Enumerated(value = EnumType.STRING)
	private PaymentMethodType method;

	private PaymentMethod(Store store, PaymentMethodType method) {
		this.store = store;
		this.method = method;
	}

	public static PaymentMethod of(Store store, PaymentMethodType type) {
		return new PaymentMethod(store, type);
	}

}
