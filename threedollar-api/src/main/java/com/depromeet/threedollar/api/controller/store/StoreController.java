package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.api.config.resolver.UserId;
import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.store.StoreImageService;
import com.depromeet.threedollar.api.service.store.StoreService;
import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StoreController {

	private final StoreService storeService;
	private final StoreImageService storeImageService;

	@Operation(summary = "가게 정보를 추가하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PostMapping("/api/v2/store")
	public ApiResponse<String> addStore(@Valid @RequestBody AddStoreRequest request, @UserId Long userId) {
		storeService.addStore(request, userId);
		return ApiResponse.SUCCESS;
	}

	@Operation(summary = "특정 가게 정보를 수정하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@PutMapping("/api/v2/store/{storeId}")
	public void updateStoreInfo(@PathVariable Long storeId, @Valid @RequestBody UpdateStoreRequest request) {

	}

	@Operation(summary = "특정 가게 정보를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@DeleteMapping("/api/v2/store/{storeId}")
	public void deleteStoreInfo(@PathVariable Long storeId) {

	}

	@Operation(summary = "특정 가게의 이미지를 등록하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@PostMapping("/api/v2/store/{storeId}/image")
	public ApiResponse<StoreImageResponse> addStoreImage(@PathVariable Long storeId, String imageUrl, @UserId Long userId) {
		return ApiResponse.success(storeImageService.addStoreImage(storeId, imageUrl, userId));
	}

	@Operation(summary = "특정 가게의 이미지를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@Auth
	@DeleteMapping("/api/v2/store/{storeId}/image")
	public ApiResponse<String> deleteStoreImage(@PathVariable Long storeId) {
		storeImageService.deleteStoreImage(storeId);
		return ApiResponse.SUCCESS;
	}

}
