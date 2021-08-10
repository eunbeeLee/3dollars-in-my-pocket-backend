package com.depromeet.threedollar.external.external.kakao.dto.component;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ConstructorBinding
@ConfigurationProperties("kakao.profile")
public class KaKaoProfileComponent {

    private final String url;

}
