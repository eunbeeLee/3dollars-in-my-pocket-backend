package com.depromeet.threedollar.external.config;

import com.depromeet.threedollar.common.exception.model.BadGatewayException;
import com.depromeet.threedollar.common.exception.model.ValidationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
            case 401:
            case 402:
            case 403:
                return new ValidationException(String.format("외부 API 호출 중 에러가 발생하였습니다. (%s) (%s) (%s)", methodKey, response.status(), response.body()));
            case 500:
            case 502:
            case 503:
                return new BadGatewayException(String.format("외부 API 연동 중 에러가 발생하였습니다. (%s) (%s)", response.status(), response.body()));
            default:
                return new IllegalArgumentException(String.format("외부 API 연동 중 에러가 발생하였습니다. (%s) (%s)", response.status(), response.body()));
        }
    }

}
