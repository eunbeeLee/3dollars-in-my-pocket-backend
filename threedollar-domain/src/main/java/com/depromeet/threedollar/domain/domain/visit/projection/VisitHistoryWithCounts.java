package com.depromeet.threedollar.domain.domain.visit.projection;

import com.depromeet.threedollar.domain.domain.visit.VisitType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class VisitHistoryWithCounts {

    private final Long storeId;

    private final VisitType visitType;

    private final long historiesCount;

    @QueryProjection
    public VisitHistoryWithCounts(Long storeId, VisitType visitType, long historiesCount) {
        this.storeId = storeId;
        this.visitType = visitType;
        this.historiesCount = historiesCount;
    }

}
