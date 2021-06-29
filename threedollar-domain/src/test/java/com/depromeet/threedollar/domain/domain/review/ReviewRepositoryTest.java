package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithCreatorDto;
import com.depromeet.threedollar.domain.domain.review.repository.dto.ReviewWithStoreAndCreatorDto;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreCreator;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserCreator;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        storeRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    void 리뷰정보와_함께_작성자_정보가_함께_가져온다() {
        User user = UserCreator.create("social-id", UserSocialType.KAKAO, "닉네임");
        userRepository.save(user);

        Store store = StoreCreator.create(user.getId(), "가게");
        storeRepository.save(store);

        Review review = ReviewCreator.create(store.getId(), user.getId(), "리뷰 1", 5);
        reviewRepository.save(review);

        // when
        List<ReviewWithCreatorDto> reviews = reviewRepository.findAllReviewWithCreatorByStoreId(store.getId());

        // then
        assertThat(reviews).hasSize(1);
        assertReviewWithCreatorDto(reviews.get(0), review.getId(), review.getRating(), review.getContents(), user.getId(), user.getName(), user.getSocialType());
    }

    private void assertReviewWithCreatorDto(ReviewWithCreatorDto review, Long id, int rating, String contents, Long userId, String name, UserSocialType socialType) {
        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getRating()).isEqualTo(rating);
        assertThat(review.getContents()).isEqualTo(contents);
        assertThat(review.getUserId()).isEqualTo(userId);
        assertThat(review.getUserName()).isEqualTo(name);
        assertThat(review.getUserSocialType()).isEqualTo(socialType);
    }

    @Test
    void 리뷰를_페이지네이션으로_가져온다() {
        User user = UserCreator.create("social-id", UserSocialType.KAKAO, "닉네임");
        userRepository.save(user);

        Store store = StoreCreator.create(user.getId(), "가게");
        storeRepository.save(store);

        reviewRepository.saveAll(Arrays.asList(
            ReviewCreator.create(store.getId(), user.getId(), "리뷰 1", 5),
            ReviewCreator.create(store.getId(), user.getId(), "리뷰 2", 4),
            ReviewCreator.create(store.getId(), user.getId(), "리뷰 3", 3)
        ));

        // when
        Page<ReviewWithStoreAndCreatorDto> pages = reviewRepository.findAllReviewWithCreatorByUserId(user.getId(), PageRequest.of(0, 2));

        // then
        assertThat(pages.getTotalElements()).isEqualTo(3);
        assertThat(pages.getTotalPages()).isEqualTo(2);
        List<ReviewWithStoreAndCreatorDto> reviews = pages.getContent();
        assertReviewWithStoreAndCreatorDto(reviews.get(0), 5, "리뷰 1", ReviewStatus.POSTED, store.getId(), store.getStoreName(), user.getId(), user.getName(), user.getSocialType());
        assertReviewWithStoreAndCreatorDto(reviews.get(1), 4, "리뷰 2", ReviewStatus.POSTED, store.getId(), store.getStoreName(), user.getId(), user.getName(), user.getSocialType());
    }

    private void assertReviewWithStoreAndCreatorDto(ReviewWithStoreAndCreatorDto review, int rating, String contents, ReviewStatus status,
                                                    Long storeId, String storeName, Long userId, String userName, UserSocialType socialType) {
        assertThat(review.getRating()).isEqualTo(rating);
        assertThat(review.getContents()).isEqualTo(contents);
        assertThat(review.getStatus()).isEqualTo(status);
        assertThat(review.getStoreId()).isEqualTo(storeId);
        assertThat(review.getStoreName()).isEqualTo(storeName);
        assertThat(review.getUserId()).isEqualTo(userId);
        assertThat(review.getUserName()).isEqualTo(userName);
        assertThat(review.getUserSocialType()).isEqualTo(socialType);
    }

}
