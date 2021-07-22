package com.depromeet.threedollar.admin.service.faq

import com.depromeet.threedollar.admin.service.faq.dto.request.AddFaqRequest
import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
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
    fun 새로운_FAQ_를_등록한다() {
        // give
        val question = "이름이 뭔가요?"
        val answer = "가슴속 삼천원"
        val category = FaqCategory.CATEGORY

        val request = AddFaqRequest(category, question, answer)

        // when
        faqService.addFaq(request)

        // then
        val faqs = faqRepository.findAll()
        assertThat(faqs).hasSize(1)
        assertFaq(faqs[0], question, answer, category)
    }

    private fun assertFaq(faq: Faq, question: String, answer: String, category: FaqCategory) {
        assertThat(faq.question).isEqualTo(question)
        assertThat(faq.answer).isEqualTo(answer)
        assertThat(faq.category).isEqualTo(category)
    }

}
