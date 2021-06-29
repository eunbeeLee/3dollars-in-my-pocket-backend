package com.depromeet.threedollar.api.service.faq.dto.response;

import com.depromeet.threedollar.domain.domain.faq.Faq;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqResponse {

	private Long id;

	private String question;

	private String answer;

	public static FaqResponse of(Faq faq) {
		return new FaqResponse(faq.getId(), faq.getQuestion(), faq.getAnswer());
	}

}
