package com.depromeet.threedollar.application.config.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ConstructorBinding
@ConfigurationProperties("spring.redis")
public class RedisProperties {

    private final String host;

    private final int port;

}
