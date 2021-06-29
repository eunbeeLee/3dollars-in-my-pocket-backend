package com.depromeet.threedollar.api.service.faq;

import com.depromeet.threedollar.api.service.faq.dto.response.FaqResponse;
import com.depromeet.threedollar.domain.domain.faq.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FaqService {

	private final FaqRepository faqRepository;

	@Transactional(readOnly = true)
	public List<FaqResponse> retrieveAllFaqs() {
		return faqRepository.findAll().stream()
				.map(FaqResponse::of)
				.collect(Collectors.toList());
	}

}
