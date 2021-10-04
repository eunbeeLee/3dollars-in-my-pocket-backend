package com.depromeet.threedollar.admin.service.admin

import com.depromeet.threedollar.admin.service.admin.dto.response.AdminInfoResponse
import com.depromeet.threedollar.domain.domain.admin.AdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val adminRepository: AdminRepository
) {

    @Transactional(readOnly = true)
    fun getMyAdminInfo(adminId: Long): AdminInfoResponse {
        val admin = AdminServiceUtils.findAdminById(adminRepository, adminId)
        return AdminInfoResponse.of(admin)
    }

}
