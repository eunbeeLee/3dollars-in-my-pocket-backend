package com.depromeet.threedollar.external.config.feign;

import com.depromeet.threedollar.common.exception.model.BadGatewayException;
import com.depromeet.threedollar.common.exception.model.ValidationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (isClientError(response.status())) {
            return new ValidationException(String.format("외부 API 호출 중 에러가 발생하였습니다. (%s) (%s) (%s)", methodKey, response.status(), response.body()));
        }
        return new BadGatewayException(String.format("외부 API 연동 중 에러가 발생하였습니다. (%s) (%s)", response.status(), response.body()));
    }

    private boolean isClientError(int status) {
        return 400 <= status && status < 500;
    }

}
