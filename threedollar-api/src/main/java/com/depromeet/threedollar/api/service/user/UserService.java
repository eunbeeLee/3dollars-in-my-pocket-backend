package com.depromeet.threedollar.api.service.user;

import com.depromeet.threedollar.domain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void createUser() {

	}

	@Transactional(readOnly = true)
	public void getUserInfo() {

	}

	@Transactional
	public void updateUserInfo() {

	}

}
