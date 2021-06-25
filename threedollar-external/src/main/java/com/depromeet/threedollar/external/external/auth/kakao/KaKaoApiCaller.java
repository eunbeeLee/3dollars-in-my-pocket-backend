package com.depromeet.threedollar.external.external.auth.kakao;

import com.depromeet.threedollar.external.external.auth.kakao.dto.response.KaKaoProfileResponse;

public interface KaKaoApiCaller {

    KaKaoProfileResponse getProfileInfo(String accessToken);

}
