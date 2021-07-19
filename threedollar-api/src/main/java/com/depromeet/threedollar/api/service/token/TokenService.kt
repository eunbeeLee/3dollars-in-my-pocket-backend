package com.depromeet.threedollar.api.service.token

import com.depromeet.threedollar.api.service.token.dto.UserTokenDto

interface TokenService {

    fun encode(userTokenDto: UserTokenDto): String

    fun decode(token: String): UserTokenDto

}
