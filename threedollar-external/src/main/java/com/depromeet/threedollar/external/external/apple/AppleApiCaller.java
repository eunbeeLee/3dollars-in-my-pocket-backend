package com.depromeet.threedollar.external.external.apple;

import com.depromeet.threedollar.external.external.apple.dto.response.ApplePublicKeyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleApiCaller {

    @GetMapping(value = "/keys")
    ApplePublicKeyResponse getAppleAuthPublicKey();

}
