package com.depromeet.threedollar.admin.controller

import com.depromeet.threedollar.admin.common.dto.ApiResponse
import com.depromeet.threedollar.admin.service.token.TokenService
import com.depromeet.threedollar.admin.service.token.dto.AdminTokenDto
import com.depromeet.threedollar.domain.domain.admin.AdminCreator
import com.depromeet.threedollar.domain.domain.admin.AdminRepository
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Profile("local")
@RestController
class TestController(
    private val tokenService: TokenService,
    private val adminRepository: AdminRepository
) {

    companion object {
        private const val TEST_ADMIN_EMAIL = "test.admin@test.com"
        private const val TEST_ADMIN_NAME = "테스트 관리자"
    }

    @GetMapping("/test-token")
    fun getTestToken(): ApiResponse<String> {
        val admin = adminRepository.findAdminByEmail(TEST_ADMIN_EMAIL)
            ?: adminRepository.save(AdminCreator.create(TEST_ADMIN_EMAIL, TEST_ADMIN_NAME))
        return ApiResponse.success(tokenService.encode(AdminTokenDto(admin.id)))
    }

}
