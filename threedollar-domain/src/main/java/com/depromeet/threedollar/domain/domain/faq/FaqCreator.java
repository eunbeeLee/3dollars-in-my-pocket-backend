package com.depromeet.threedollar.domain.domain.faq;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqCreator {

    public static Faq create(String question, String answer, FaqCategory category) {
        return Faq.newInstance(category, question, answer);
    }

}
