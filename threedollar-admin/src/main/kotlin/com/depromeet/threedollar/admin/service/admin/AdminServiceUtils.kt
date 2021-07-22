package com.depromeet.threedollar.admin.service.admin

import com.depromeet.threedollar.common.exception.NotFoundException
import com.depromeet.threedollar.domain.domain.admin.Admin
import com.depromeet.threedollar.domain.domain.admin.AdminRepository

object AdminServiceUtils {

    fun findAdminById(adminRepository: AdminRepository, adminId: Long): Admin {
        return adminRepository.findAdminById(adminId)
            ?: throw NotFoundException("해당하는 관리자 ($adminId)는 존재하지 않습니다")
    }

}
