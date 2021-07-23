package com.depromeet.threedollar.api.service.review.dto.response;

import com.depromeet.threedollar.domain.domain.review.repository.projection.ReviewWithStoreAndCreatorProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDetailWithPaginationResponse {

    private final List<ReviewDetailResponse> content = new ArrayList<>();
    private long totalElements;
    private long totalPages;

    private ReviewDetailWithPaginationResponse(List<ReviewDetailResponse> reviews, long totalElements, long totalPages) {
        this.content.addAll(reviews);
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static ReviewDetailWithPaginationResponse of(Page<ReviewWithStoreAndCreatorProjection> reviewDto) {
        List<ReviewDetailResponse> responses = reviewDto.getContent().stream()
            .map(ReviewDetailResponse::of)
            .collect(Collectors.toList());
        return new ReviewDetailWithPaginationResponse(responses, reviewDto.getTotalElements(), reviewDto.getTotalPages());
    }

}
