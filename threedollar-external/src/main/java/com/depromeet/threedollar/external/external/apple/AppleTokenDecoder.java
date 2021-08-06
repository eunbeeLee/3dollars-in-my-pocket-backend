package com.depromeet.threedollar.external.external.apple;

import com.depromeet.threedollar.external.external.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
