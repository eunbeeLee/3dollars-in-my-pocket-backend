package com.depromeet.threedollar.admin.controller.admin

import com.depromeet.threedollar.admin.config.resolver.AdminId
import com.depromeet.threedollar.admin.service.admin.AdminService
import com.depromeet.threedollar.admin.service.admin.dto.response.AdminInfoResponse
import com.depromeet.threedollar.application.common.dto.ApiResponse
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(
    private val adminService: AdminService
) {

    @ApiOperation("자신의 관리자 정보를 조회합니다.")
    @GetMapping("/admin/v1/admin/me")
    fun getMyAdminInfo(@AdminId adminId: Long): ApiResponse<AdminInfoResponse> {
        return ApiResponse.success(adminService.getMyAdminInfo(adminId))
    }

}
