package com.depromeet.threedollar.domain.domain.review.repository.dto;

import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewWithCreator {

	private Long id;

	private int rating;

	private String contents;

	private Long userId;

	private String userName;

	private UserSocialType userSocialType;

}
