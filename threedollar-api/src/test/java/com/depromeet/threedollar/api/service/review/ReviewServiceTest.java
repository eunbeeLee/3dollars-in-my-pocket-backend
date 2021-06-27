package com.depromeet.threedollar.api.service.review;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.domain.domain.review.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewServiceTest extends UserSetUpTest {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReviewRepository reviewRepository;

	@AfterEach
	void cleanUp() {
		super.cleanup();
		reviewRepository.deleteAll();
	}

	@Test
	void 새로운_리뷰를_작성한다() {
		// given

	}

}