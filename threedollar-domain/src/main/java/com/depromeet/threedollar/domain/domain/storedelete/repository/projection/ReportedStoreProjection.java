package com.depromeet.threedollar.domain.domain.storedelete.repository.projection;

import com.depromeet.threedollar.domain.domain.store.StoreType;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportedStoreProjection {

    private Long storeId;
    private String storeName;
    private double latitude;
    private double longitude;
    private StoreType type;
    private double rating;
    private LocalDateTime storeCreatedAt;
    private LocalDateTime storeUpdatedAt;
    private long reportsCount;

}
