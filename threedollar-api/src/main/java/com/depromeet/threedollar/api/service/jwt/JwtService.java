package com.depromeet.threedollar.api.service.jwt;

public interface JwtService {

    String encode(Long userId);

    Long decode(String token);

}
