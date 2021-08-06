package com.depromeet.threedollar.external.external.kakao;

import com.depromeet.threedollar.external.external.kakao.dto.response.KaKaoProfileResponse;

public interface KaKaoApiCaller {

    KaKaoProfileResponse getProfileInfo(String accessToken);

}
