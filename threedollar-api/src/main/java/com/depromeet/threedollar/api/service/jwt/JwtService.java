package com.depromeet.threedollar.api.service.jwt;

public interface JwtService {

	String encodeSignUpToken(Long userId);

	Long decodeSignUpToken(String token);

}
