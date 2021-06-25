package com.depromeet.threedollar.api.service.user;

import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {

	static void validateNotExistsUser(UserRepository userRepository, String socialId, UserSocialType socialType) {
		User user = userRepository.findUserBySocialIdAndSocialType(socialId, socialType);
		if (user != null) {
			throw new IllegalArgumentException(String.format("이미 존재하는 유저 (%s - %s) 입니다", socialId, socialType));
		}
	}

	static void validateNotExistsUserName(UserRepository userRepository, String name) {
		User user = userRepository.findUserByName(name);
		if (user != null) {
			throw new IllegalArgumentException(String.format("이미 존재하는 닉네임 (%s) 입니다", name));
		}
	}

	public static User findUserById(UserRepository userRepository, Long userId) {
		User user = userRepository.findUserById(userId);
		if (user == null) {
			throw new IllegalArgumentException(String.format("존재하지 않는 유저 (%s) 입니다", userId));
		}
		return user;
	}

	public static void validateExistsUser(UserRepository userRepository, Long userId) {
		User user = userRepository.findUserById(userId);
		if (user == null) {
			throw new IllegalArgumentException(String.format("존재하지 않는 유저 (%s) 입니다", userId));
		}
	}

	public static User findUserBySocialIdAndSocialType(UserRepository userRepository, String socialId, UserSocialType socialType) {
		User user = userRepository.findUserBySocialIdAndSocialType(socialId, socialType);
		if (user == null) {
			throw new IllegalArgumentException(String.format("존재하지 않는 유저 (%s - %s) 입니다", socialId, socialType));
		}
		return user;
	}

}
