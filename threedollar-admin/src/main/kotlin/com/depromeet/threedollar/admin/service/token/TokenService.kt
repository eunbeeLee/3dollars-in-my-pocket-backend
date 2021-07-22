package com.depromeet.threedollar.admin.service.token

import com.depromeet.threedollar.admin.service.token.dto.AdminTokenDto

interface TokenService {

    fun encode(adminTokenDto: AdminTokenDto): String

    fun decode(token: String): AdminTokenDto

}
