package com.depromeet.threedollar.api.config.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

import static com.depromeet.threedollar.common.utils.ProcessUtils.findAvailableRandomPort;
import static com.depromeet.threedollar.common.utils.ProcessUtils.isRunningPort;

/**
 * 로컬 및 테스트용 임베디드 Redis Server 설정.
 */
@Slf4j
@Profile({"local", "local-will"})
@RequiredArgsConstructor
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 15) // 15일 만료
public class EmbeddedRedisSessionConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, redisPort);
    }

    @PostConstruct
    public void redisServer() throws IOException {
        redisPort = isRunningPort(redisPort) ? findAvailableRandomPort() : redisPort;
        log.info("인메모리 레디스 서버가 기동되었습니다. port {}", redisPort);
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            log.info("인메모리 레디스 서버가 종료됩니다. port: {}", redisPort);
        }
    }

}
