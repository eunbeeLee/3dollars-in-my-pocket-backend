package com.depromeet.threedollar.api.service.review.dto.request;

import lombok.*;

import javax.validation.constraints.Min;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveMyReviewsRequest {

    @Min(value = 1, message = "{common.page.min}")
    private int size;

    @Min(value = 0, message = "{common.size.min}")
    private int page;

}
