package com.depromeet.threedollar.external.external.auth.apple;

import com.depromeet.threedollar.external.external.auth.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
