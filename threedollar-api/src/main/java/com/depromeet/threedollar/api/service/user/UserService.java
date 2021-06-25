package com.depromeet.threedollar.api.service.user;

import com.depromeet.threedollar.api.service.user.dto.request.CreateUserRequest;
import com.depromeet.threedollar.api.service.user.dto.request.UpdateUserInfoRequest;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public Long createUser(CreateUserRequest request) {
		UserServiceUtils.validateNotExistsUser(userRepository, request.getSocialId(), request.getSocialType());
		UserServiceUtils.validateNotExistsUserName(userRepository, request.getName());
		return userRepository.save(request.toEntity()).getId();
	}

	@Transactional(readOnly = true)
	public UserInfoResponse getUserInfo(Long userId) {
		User user = UserServiceUtils.findUserById(userRepository, userId);
		return UserInfoResponse.of(user);
	}

	@Transactional
	public UserInfoResponse updateUserInfo(UpdateUserInfoRequest request, Long userId) {
		User user = UserServiceUtils.findUserById(userRepository, userId);
		user.update(request.getName());
		return UserInfoResponse.of(user);
	}

}
