package com.depromeet.threedollar.application.config.cache;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CacheType {

    FAQS(CacheKey.FAQS, Duration.ofHours(1)),
    USER_STORES(CacheKey.USER_STORES, Duration.ofMinutes(5)),
    ;

    private final String key;
    private final Duration duration;

    public static class CacheKey {

        public static final String FAQS = "FAQS";
        public static final String USER_STORES = "USER_STORES";

    }

}
