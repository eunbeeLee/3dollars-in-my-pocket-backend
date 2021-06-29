package com.depromeet.threedollar.api.controller.faq;

import com.depromeet.threedollar.api.controller.ApiResponse;
import com.depromeet.threedollar.api.service.faq.FaqService;
import com.depromeet.threedollar.api.service.faq.dto.response.FaqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FaqController {

    private final FaqService faqService;

    @GetMapping("/api/v1/faqs")
    public ApiResponse<List<FaqResponse>> retrieveAllFaqs() {
        return ApiResponse.success(faqService.retrieveAllFaqs());
    }

}
