package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.api.service.review.dto.request.AddReviewRequest;
import com.depromeet.threedollar.api.service.review.dto.request.UpdateReviewRequest;
import com.depromeet.threedollar.domain.domain.review.Review;
import com.depromeet.threedollar.domain.domain.review.ReviewCreator;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import com.depromeet.threedollar.domain.domain.review.ReviewStatus;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreCreator;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReviewServiceTest extends UserSetUpTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Store store;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        storeRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @BeforeEach
    void setUpStore() {
        store = StoreCreator.create(userId, "디프만 붕어빵");
        storeRepository.save(store);
    }

    @Test
    void 새로운_리뷰를_작성한다() {
        // given
        String contents = "우와 맛있어요";
        int rating = 4;
        AddReviewRequest request = AddReviewRequest.testInstance(store.getId(), contents, rating);

        // when
        reviewService.addReview(request, userId);

        // then
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(1);
        assertReview(reviewList.get(0), store.getId(), contents, rating, userId);
    }

    @Test
    void 리뷰_작성시_해당하는_가게가_존재하지_않는경우_에러가_발생한다() {
        // given
        Long storeId = 999L;
        AddReviewRequest request = AddReviewRequest.testInstance(storeId, "리뷰", 3);

        // when & then
        assertThatThrownBy(() -> reviewService.addReview(request, userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 새로운_리뷰를_작성하면_Store의_평균_리뷰점수도_계산된다() {
        // given
        String contents = "우와 맛있어요";
        int rating = 4;

        AddReviewRequest request = AddReviewRequest.testInstance(store.getId(), contents, rating);

        // when
        reviewService.addReview(request, userId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getRating()).isEqualTo(4.0);
    }

    @Test
    void 작성한_리뷰를_작성하면_가게의_평균_평가_점수가_다시_계산된다() {
        // given
        String contents = "우와 맛있어요";
        int rating = 4;

        reviewRepository.save(ReviewCreator.create(store.getId(), userId, "맛 없어요", 1));

        // when
        AddReviewRequest request = AddReviewRequest.testInstance(store.getId(), contents, rating);

        // when
        reviewService.addReview(request, userId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getRating()).isEqualTo(2.5); // (4 + 1) / 2 = 2.5
    }

    @Test
    void 리뷰를_수정하면_해당하는_DB에_리뷰_데이터가_수정된다() {
        // given
        String contents = "우와 맛있어요";
        int rating = 4;

        Review review = ReviewCreator.create(store.getId(), userId, "너무 맛있어요", 3);
        reviewRepository.save(review);

        UpdateReviewRequest request = UpdateReviewRequest.testInstance(contents, rating);

        // when
        reviewService.updateReview(review.getId(), request, userId);

        // then
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(1);
        assertReview(reviewList.get(0), store.getId(), contents, rating, userId);
    }

    @Test
    void 리뷰를_수정하면_가게의_평균_리뷰_점수가_다시_계산된다() {
        // given
        String contents = "우와 맛있어요";
        int rating = 4;

        Review review = ReviewCreator.create(store.getId(), userId, "맛 없어요", 1);
        reviewRepository.saveAll(Arrays.asList(ReviewCreator.create(store.getId(), userId, "맛 없어요", 2), review));

        // when
        UpdateReviewRequest request = UpdateReviewRequest.testInstance(contents, rating);

        // when
        reviewService.updateReview(review.getId(), request, userId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getRating()).isEqualTo(3); // (2 + 4 / 2 = 3)
    }

    @Test
    void 작성한_리뷰를_수정시_해당하는_리뷰가_없을경우_NOT_FOUND_EXCEPTION_이_발생한다() {
        // given
        UpdateReviewRequest request = UpdateReviewRequest.testInstance("content", 5);

        // when & then
        assertThatThrownBy(() -> reviewService.updateReview(999L, request, userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 작성한_리뷰_수정시_해당하는_리뷰가_사용자가_작성하지_않았을경우_NOT_FOUND_EXCEPTION() {
        // given
        Review review = ReviewCreator.create(store.getId(), userId, "너무 맛있어요", 3);
        reviewRepository.save(review);

        UpdateReviewRequest request = UpdateReviewRequest.testInstance("content", 5);

        // when & then
        assertThatThrownBy(() -> reviewService.updateReview(review.getId(), request, 999L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 작성한_리뷰를_삭제한다() {
        // given
        Review review = ReviewCreator.create(store.getId(), userId, "너무 맛있어요", 3);
        reviewRepository.save(review);

        // when
        reviewService.deleteReview(review.getId(), userId);

        // then
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(1);
        assertThat(reviewList.get(0).getStatus()).isEqualTo(ReviewStatus.DELETED);
    }

    @Test
    void 리뷰를_삭제하면_가게_평균_리뷰_점수가_갱신되며_리뷰를_삭제해서_아무_리뷰가_없는경우_0점으로_표기된다() {
        // given
        Review review = ReviewCreator.create(store.getId(), userId, "너무 맛있어요", 3);
        reviewRepository.save(review);

        // when
        reviewService.deleteReview(review.getId(), userId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getRating()).isEqualTo(0);
    }

    @Test
    void 작성한_리뷰를_삭제시_해당하는_리뷰가_없을경우_NOT_FOUND_EXCEPTION_이_발생한다() {
        // when & then
        assertThatThrownBy(() -> reviewService.deleteReview(999L, userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 리뷰_삭제시_해당하는_리뷰가_사용자가_작성하지_않았을경우_NOT_FOUND_EXCEPTION() {
        // given
        Review review = ReviewCreator.create(store.getId(), userId, "너무 맛있어요", 3);
        reviewRepository.save(review);

        // when & then
        assertThatThrownBy(() -> reviewService.deleteReview(review.getId(), 999L)).isInstanceOf(NotFoundException.class);
    }

    private void assertReview(Review review, Long storeId, String contents, int rating, Long userId) {
        assertThat(review.getStoreId()).isEqualTo(storeId);
        assertThat(review.getContents()).isEqualTo(contents);
        assertThat(review.getRating()).isEqualTo(rating);
        assertThat(review.getUserId()).isEqualTo(userId);
    }

}
