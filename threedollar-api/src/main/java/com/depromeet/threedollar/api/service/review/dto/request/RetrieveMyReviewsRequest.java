package com.depromeet.threedollar.api.service.review.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveMyReviewsRequest {

    @Min(value = 1, message = "{common.page.min}")
    private int size;

    @Min(value = 0, message = "{common.size.min}")
    private int page;

}
