package com.depromeet.threedollar.domain.domain.admin;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdminCreator {

    public static Admin create(String email, String name) {
        return Admin.newInstance(email, name);
    }

}
