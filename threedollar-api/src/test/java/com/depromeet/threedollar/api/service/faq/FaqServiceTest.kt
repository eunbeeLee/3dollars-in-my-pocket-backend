package com.depromeet.threedollar.api.service.faq

import com.depromeet.threedollar.api.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
import com.depromeet.threedollar.domain.domain.faq.FaqCreator
import com.depromeet.threedollar.domain.domain.faq.FaqRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class FaqServiceTest(
    @Autowired
    private val faqService: FaqService,

    @Autowired
    private val faqRepository: FaqRepository
) {

    @AfterEach
    fun cleanUp() {
        faqRepository.deleteAll()
    }

    @Test
    fun 모든_FAQ_를_조회한다() {
        // given
        val question = "질문"
        val answer = "대답"
        val category = FaqCategory.CATEGORY
        faqRepository.save(FaqCreator.create(question, answer, category));

        // when
        val result = faqService.retrieveAllFaqs()

        // then
        assertThat(result).hasSize(1)
        assertFaq(result, question, answer, category)
    }

    private fun assertFaq(responses: List<FaqResponse>, question: String, answer: String, category: FaqCategory) {
        assertThat(responses[0].question).isEqualTo(question)
        assertThat(responses[0].answer).isEqualTo(answer)
        assertThat(responses[0].category).isEqualTo(category)
    }

}
