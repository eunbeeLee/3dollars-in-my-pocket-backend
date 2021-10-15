package com.depromeet.threedollar.api.controller.menu

import com.depromeet.threedollar.application.common.dto.ApiResponse
import com.depromeet.threedollar.application.mapper.menu.MenuCategoryMapper
import com.depromeet.threedollar.application.mapper.menu.dto.response.MenuCategoryResponse
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MenuCategoryController {

    @ApiOperation("활성화된 메뉴 카테고리의 정보들을 조회한다")
    @GetMapping("/api/v2/store/menu/categories")
    fun retrieveStoreMenuCategories(): ApiResponse<List<MenuCategoryResponse>> {
        return ApiResponse.success(MenuCategoryMapper.retrieveMenuCategories())
    }

}
