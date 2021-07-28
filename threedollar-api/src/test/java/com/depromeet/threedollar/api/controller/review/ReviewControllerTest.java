package com.depromeet.threedollar.api.controller.review;

import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.controller.AbstractControllerTest;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.RetrieveMyReviewsRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewInfoResponse;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewDetailResponse;
import com.depromeet.threedollar.api.service.review.dto.response.ReviewDetailWithPaginationResponse;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewCreator;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreCreator;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewControllerTest extends AbstractControllerTest {

    private ReviewMockApiCaller reviewMockApiCaller;

    private Store store;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        reviewMockApiCaller = new ReviewMockApiCaller(mockMvc, objectMapper);

        store = StoreCreator.create(testUser.getId(), "디프만 붕어빵");
        storeRepository.save(store);
    }

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StoreRepository storeRepository;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        reviewRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @DisplayName("POST /api/v2/store/review 200 OK")
    @Test
    void 가게에_새로운_리뷰를_등록합니다() throws Exception {
        // given
        AddReviewRequest request = AddReviewRequest.testInstance(store.getId(), "content", 5);

        // when
        ApiResponse<ReviewInfoResponse> response = reviewMockApiCaller.addStoreReview(request, token, 200);

        // then
        assertReviewInfoResponse(response.getData(), store.getId(), request.getContent(), request.getRating(), ReviewStatus.POSTED);
    }

    @DisplayName("PUT /api/v2/store/review 200 OK")
    @Test
    void 사용자가_작성한_리뷰를_수정하는_API_호출시_200_OK() throws Exception {
        // given
        Review review = ReviewCreator.create(store.getId(), testUser.getId(), "너무 맛있어요", 5);
        reviewRepository.save(review);

        UpdateReviewRequest request = UpdateReviewRequest.testInstance("맛이 없어졌어요", 1);

        // when
        ApiResponse<ReviewInfoResponse> response = reviewMockApiCaller.updateStoreReview(review.getId(), request, token, 200);

        // then
        assertReviewInfoResponse(response.getData(), store.getId(), request.getContent(), request.getRating(), ReviewStatus.POSTED);
    }

    @DisplayName("DELETE /api/v2/store/review 200 OK")
    @Test
    void 사용자가_작성한_리뷰를_삭제하는_API_호출시_200_OK() throws Exception {
        // given
        Review review = ReviewCreator.create(store.getId(), testUser.getId(), "너무 맛있어요", 5);
        reviewRepository.save(review);

        // when
        ApiResponse<String> response = reviewMockApiCaller.deleteStoreReview(review.getId(), token, 200);

        // then
        assertThat(response.getData()).isEqualTo("OK");
    }

    @DisplayName("GET /api/v2/store/reviews/me 200 OK")
    @Test
    void 사용자가_작성한_리뷰를_전체_조회하는_API_호출시_200_OK() throws Exception {
        // given
        Review review1 = ReviewCreator.create(store.getId(), testUser.getId(), "너무 맛있어요", 5);
        Review review2 = ReviewCreator.create(store.getId(), testUser.getId(), "너무 맛있어요", 4);
        reviewRepository.saveAll(Arrays.asList(review1, review2));

        RetrieveMyReviewsRequest request = RetrieveMyReviewsRequest.testInstance(2, 0);

        // when
        ApiResponse<ReviewDetailWithPaginationResponse> response = reviewMockApiCaller.retrieveMyStoreReviews(request, token, 200);

        // then
        assertThat(response.getData().getTotalPages()).isEqualTo(1);
        assertThat(response.getData().getTotalElements()).isEqualTo(2);
        assertThat(response.getData().getContent()).hasSize(2);

        assertReviewInfoResponse(response.getData().getContent().get(0), review2.getId(), store.getId(), store.getName(), review2.getContents(), review2.getRating(), ReviewStatus.POSTED);
        assertUserInfoResponse(response.getData().getContent().get(0).getUser(), testUser.getId(), testUser.getName(), testUser.getSocialType());

        assertReviewInfoResponse(response.getData().getContent().get(1), review1.getId(), store.getId(), store.getName(), review1.getContents(), review1.getRating(), ReviewStatus.POSTED);
        assertUserInfoResponse(response.getData().getContent().get(1).getUser(), testUser.getId(), testUser.getName(), testUser.getSocialType());
    }

    @DisplayName("GET /api/v2/store/reviews/me 삭제된 리뷰는 조회되지 않는다.")
    @Test
    void 사용자가_작성한_리뷰를_전체조회시_삭제된_리뷰는_조회되지_않는다() throws Exception {
        // given
        Review review = ReviewCreator.create(store.getId(), testUser.getId(), "너무 맛있어요", 5);
        review.delete();
        reviewRepository.save(review);

        RetrieveMyReviewsRequest request = RetrieveMyReviewsRequest.testInstance(2, 0);

        // when
        ApiResponse<ReviewDetailWithPaginationResponse> response = reviewMockApiCaller.retrieveMyStoreReviews(request, token, 200);

        // then
        assertThat(response.getData().getTotalPages()).isEqualTo(0);
        assertThat(response.getData().getTotalElements()).isEqualTo(0);
        assertThat(response.getData().getContent()).isEmpty();
    }

    private void assertUserInfoResponse(UserInfoResponse user, Long id, String name, UserSocialType socialType) {
        assertThat(user.getUserId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSocialType()).isEqualTo(socialType);
    }

    private void assertReviewInfoResponse(ReviewInfoResponse response, Long storeId, String content, int rating, ReviewStatus status) {
        assertThat(response.getStoreId()).isEqualTo(storeId);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getRating()).isEqualTo(rating);
        assertThat(response.getStatus()).isEqualTo(status);
    }

    private void assertReviewInfoResponse(ReviewDetailResponse response, Long reviewId, Long storeId, String storeName, String content, int rating, ReviewStatus status) {
        assertThat(response.getStoreId()).isEqualTo(storeId);
        assertThat(response.getStoreName()).isEqualTo(storeName);
        assertThat(response.getContents()).isEqualTo(content);
        assertThat(response.getRating()).isEqualTo(rating);
        assertThat(response.getStatus()).isEqualTo(status);
        assertThat(response.getReviewId()).isEqualTo(reviewId);
    }

}
