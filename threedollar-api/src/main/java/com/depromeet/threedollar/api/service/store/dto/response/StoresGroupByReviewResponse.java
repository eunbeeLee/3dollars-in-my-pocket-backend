package com.depromeet.threedollar.api.service.store.dto.response;

import com.depromeet.threedollar.common.utils.type.StoreReviewGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoresGroupByReviewResponse {

    private final List<StoreInfoResponse> storeList0 = new ArrayList<>();
    private final List<StoreInfoResponse> storeList1 = new ArrayList<>();
    private final List<StoreInfoResponse> storeList2 = new ArrayList<>();
    private final List<StoreInfoResponse> storeList3 = new ArrayList<>();
    private final List<StoreInfoResponse> storeList4 = new ArrayList<>();

    private StoresGroupByReviewResponse(List<StoreInfoResponse> stores) {
        for (StoreInfoResponse store : stores) {
            StoreReviewGroup group = StoreReviewGroup.of(store.getRating());
            switch (group) {
                case ZERO_TO_ONE:
                    storeList0.add(store);
                    break;
                case ONE_TO_TWO:
                    storeList1.add(store);
                    break;
                case TWO_TO_THREE:
                    storeList2.add(store);
                    break;
                case THREE_TO_FOUR:
                    storeList3.add(store);
                    break;
                case FOUR_TO_FIVE:
                    storeList4.add(store);
                    break;
                default:
                    throw new IllegalArgumentException(String.format("예상치 못한 거리가 입력되었습니다 (%s)", store.getDistance()));
            }
        }
    }

    public static StoresGroupByReviewResponse of(List<StoreInfoResponse> stores) {
        return new StoresGroupByReviewResponse(stores);
    }

}
