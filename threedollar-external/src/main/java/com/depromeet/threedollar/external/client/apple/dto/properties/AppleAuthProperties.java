package com.depromeet.threedollar.external.client.apple.dto.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ConstructorBinding
@ConfigurationProperties("apple")
public class AppleAuthProperties {

    private final String issuer;

    private final String clientId;

}
