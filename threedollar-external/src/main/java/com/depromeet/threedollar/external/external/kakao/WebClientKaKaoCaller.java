package com.depromeet.threedollar.external.external.kakao;

import com.depromeet.threedollar.common.exception.BadGatewayException;
import com.depromeet.threedollar.external.external.kakao.dto.component.KaKaoProfileComponent;
import com.depromeet.threedollar.external.external.kakao.dto.response.KaKaoProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientKaKaoCaller implements KaKaoApiCaller {

    private final WebClient webClient;
    private final KaKaoProfileComponent kaKaoProfileComponent;

    @Override
    public KaKaoProfileResponse getProfileInfo(String accessToken) {
        return webClient.get()
            .uri(kaKaoProfileComponent.getUrl())
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BadGatewayException(String.format("잘못된 카카오 액세스 토큰 (%s) 입니다", accessToken))))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BadGatewayException("카카오 로그인 연동 중 에러가 발생하였습니다")))
            .bodyToMono(KaKaoProfileResponse.class)
            .block();
    }

}
