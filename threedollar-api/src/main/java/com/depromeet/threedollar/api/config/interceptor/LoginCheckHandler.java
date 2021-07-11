package com.depromeet.threedollar.api.config.interceptor;

import com.depromeet.threedollar.api.service.jwt.JwtService;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.common.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginCheckHandler {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public Long getUserId(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Long userId = jwtService.decode(token);
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UnAuthorizedException(String.format("존재하지 않는 유저 (%s) 입니다", userId));
        }
        return userId;
    }

}
