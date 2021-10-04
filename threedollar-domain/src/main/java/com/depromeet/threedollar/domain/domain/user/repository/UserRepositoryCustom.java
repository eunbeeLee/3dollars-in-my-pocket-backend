package com.depromeet.threedollar.domain.domain.user.repository;

import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;

public interface UserRepositoryCustom {

    boolean existsByName(String name);

    boolean existsBySocialIdAndSocialType(String socialId, UserSocialType socialType);

    User findUserBySocialIdAndSocialType(String socialId, UserSocialType type);

    User findUserById(Long userId);

}
