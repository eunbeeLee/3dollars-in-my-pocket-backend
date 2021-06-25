package com.depromeet.threedollar.api.controller.store;

import org.springframework.web.bind.annotation.*;

@RestController
public class StoreController {

	/**
	 * 가게 정보를 저장하는 API
	 * 기존: /api/v1/store/save
	 */
	@PostMapping("/api/v1/store")
	public void addStore() {

	}

	/**
	 * 특정 가게 정보를 수정하는 API
	 */
	@PutMapping("/api/v1/store")
	public void updateStoreInfo() {

	}

	/**
	 * 특정 가게 정보를 삭제하는 API
	 */
	@DeleteMapping("/api/v1/store")
	public void deleteStoreInfo() {

	}

	/**
	 * 특정 가게의 이미지를 등록하는 API
	 */
	@PostMapping("/api/v1/store/image")
	public void addStoreImage() {

	}

	/**
	 * 특정 가게의 이미지를 삭제하는 API
	 */
	@DeleteMapping("/api/v1/store/image")
	public void deleteStoreImage() {

	}

}
