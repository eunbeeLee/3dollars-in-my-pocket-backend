package com.depromeet.threedollar.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/ping")
	public ApiResponse<String> healthCheck() {
		return ApiResponse.SUCCESS;
	}

}
