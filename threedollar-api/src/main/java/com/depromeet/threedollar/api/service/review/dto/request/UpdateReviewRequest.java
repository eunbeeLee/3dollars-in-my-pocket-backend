package com.depromeet.threedollar.api.service.review.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReviewRequest {

    @NotBlank(message = "{review.content.notBlank}")
    private String content;

    @Min(value = 0, message = "{review.rating.min}")
    @Max(value = 5, message = "{review.rating.max}")
    private int rating;

    public static UpdateReviewRequest testInstance(String content, int rating) {
        return new UpdateReviewRequest(content, rating);
    }

}
