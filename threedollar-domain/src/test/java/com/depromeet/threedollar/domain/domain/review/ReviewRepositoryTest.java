package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.domain.domain.review.projection.ReviewWithWriterProjection;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreCreator;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserCreator;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Nested
    class findAllWithCreatorByStoreId {

        @Test
        void 가게_리뷰와_함께_리뷰_작성자_정보를_함께_조회한다() {
            User user = UserCreator.create("social-id", UserSocialType.KAKAO, "닉네임");
            userRepository.save(user);

            Store store = StoreCreator.create(user.getId(), "가게");
            storeRepository.save(store);

            Review review = ReviewCreator.create(store.getId(), user.getId(), "리뷰 1", 5);
            reviewRepository.save(review);

            // when
            List<ReviewWithWriterProjection> reviews = reviewRepository.findAllWithCreatorByStoreId(store.getId());

            // then
            assertThat(reviews).hasSize(1);
            assertReviewWithCreatorDto(reviews.get(0), review.getId(), review.getRating(), review.getContents(), user.getId(), user.getName(), user.getSocialType());
        }

    }

    private void assertReviewWithCreatorDto(ReviewWithWriterProjection review, Long reviewId, int rating, String contents, Long userId, String name, UserSocialType socialType) {
        assertThat(review.getReviewId()).isEqualTo(reviewId);
        assertThat(review.getRating()).isEqualTo(rating);
        assertThat(review.getContents()).isEqualTo(contents);
        assertThat(review.getUserId()).isEqualTo(userId);
        assertThat(review.getUserName()).isEqualTo(name);
        assertThat(review.getUserSocialType()).isEqualTo(socialType);
    }

}
