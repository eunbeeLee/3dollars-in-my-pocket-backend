package com.depromeet.threedollar.domain.domain.faq.repository;

import com.depromeet.threedollar.domain.domain.faq.Faq;
import com.depromeet.threedollar.domain.domain.faq.FaqCategory;

import java.util.List;

public interface FaqRepositoryCustom {

    List<Faq> findAllByCategory(FaqCategory category);

    Faq findFaqById(long faqId);

}
