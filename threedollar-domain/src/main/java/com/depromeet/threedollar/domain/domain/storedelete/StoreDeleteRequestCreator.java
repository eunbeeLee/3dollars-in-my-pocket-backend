package com.depromeet.threedollar.domain.domain.storedelete;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreDeleteRequestCreator {

    public static StoreDeleteRequest create(Long storeId, Long userId, DeleteReasonType type) {
        return StoreDeleteRequest.of(storeId, userId, type);
    }

}
