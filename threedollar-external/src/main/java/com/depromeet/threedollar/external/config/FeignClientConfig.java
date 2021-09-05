package com.depromeet.threedollar.external.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.depromeet.threedollar")
@Configuration
public class FeignClientConfig {

}
