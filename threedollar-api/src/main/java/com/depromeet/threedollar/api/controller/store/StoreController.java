package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.store.StoreImageService;
import com.depromeet.threedollar.api.service.store.StoreService;
import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreImageResponse;
import com.depromeet.threedollar.api.service.store.dto.response.StoreInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StoreController {

	private final StoreService storeService;
	private final StoreImageService storeImageService;

	@Operation(summary = "가게 정보를 추가하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PostMapping("/api/v2/store")
	public ApiResponse<StoreInfoResponse> addStore(@Valid @RequestBody AddStoreRequest request, @UserId Long userId) {
		return ApiResponse.success(storeService.addStore(request, userId));
	}

	@Operation(summary = "특정 가게 정보를 수정하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PutMapping("/api/v2/store/{storeId}")
	public ApiResponse<StoreInfoResponse> updateStoreInfo(@PathVariable Long storeId, @Valid @RequestBody UpdateStoreRequest request, @UserId Long userId) {
		return ApiResponse.success(storeService.updateStore(storeId, request, userId));
	}

	@Operation(summary = "특정 가게 정보를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@DeleteMapping("/api/v2/store/{storeId}")
	public ApiResponse<String> deleteStoreInfo(@Valid DeleteStoreRequest request, @PathVariable Long storeId, @UserId Long userId) {
		storeService.deleteStore(storeId, request, userId);
		return ApiResponse.SUCCESS;
	}

	@Operation(summary = "특정 가게의 이미지를 등록하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PostMapping("/api/v2/store/{storeId}/images")
	public ApiResponse<StoreImageResponse> addStoreImage(@PathVariable Long storeId, @RequestPart(value = "image") MultipartFile multipartFile, @UserId Long userId) {
		return ApiResponse.success(storeImageService.addStoreImage(storeId, multipartFile, userId));
	}

	@Operation(summary = "특정 가게의 이미지를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@DeleteMapping("/api/v2/store/images/{imageId}")
	public ApiResponse<String> deleteStoreImage(@PathVariable Long imageId) {
		storeImageService.deleteStoreImage(imageId);
		return ApiResponse.SUCCESS;
	}

}
