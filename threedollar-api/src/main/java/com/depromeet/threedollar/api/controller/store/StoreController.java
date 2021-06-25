package com.depromeet.threedollar.api.controller.store;

import com.depromeet.threedollar.api.service.store.dto.request.UpdateStoreRequest;
import com.depromeet.threedollar.api.service.store.dto.request.AddStoreRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
public class StoreController {

	@Operation(summary = "가게 정보를 추가하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@PostMapping("/api/v2/store")
	public void addStore(@Valid @RequestBody AddStoreRequest request) {

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
	@PostMapping("/api/v2/store/{storeId}/image")
	public void addStoreImage(@PathVariable Long storeId, @RequestPart(value = "image") MultipartFile multipartFile) {

	}

	@Operation(summary = "특정 가게의 이미지를 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
	@DeleteMapping("/api/v2/store/{storeId}/image")
	public void deleteStoreImage(@PathVariable Long storeId) {

	}

}
