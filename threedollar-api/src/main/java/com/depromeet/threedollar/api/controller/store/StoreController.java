package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.application.common.dto.ApiResponse;
import com.depromeet.threedollar.api.service.store.StoreService;
import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreDeleteResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @ApiOperation("[인증] 새로운 가게 정보를 등록합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/api/v2/store")
    public ApiResponse<StoreInfoResponse> addStore(@Valid @RequestBody AddStoreRequest request, @UserId Long userId) {
        return ApiResponse.success(storeService.addStore(request, userId));
    }

    @ApiOperation("[인증] 특정 가게의 정보를 수정합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/api/v2/store/{storeId}")
    public ApiResponse<StoreInfoResponse> updateStore(@PathVariable Long storeId, @Valid @RequestBody UpdateStoreRequest request, @UserId Long userId) {
        return ApiResponse.success(storeService.updateStore(storeId, request, userId));
    }

    @ApiOperation("[인증] 특정 가게의 정보를 삭제 요청합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("/api/v2/store/{storeId}")
    public ApiResponse<StoreDeleteResponse> deleteStore(@Valid DeleteStoreRequest request, @PathVariable Long storeId, @UserId Long userId) {
        return ApiResponse.success(storeService.deleteStore(storeId, request, userId));
    }

}
