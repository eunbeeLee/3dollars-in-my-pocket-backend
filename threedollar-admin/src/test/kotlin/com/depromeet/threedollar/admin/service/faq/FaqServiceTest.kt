package com.depromeet.threedollar.admin.service.faq

import com.depromeet.threedollar.admin.service.faq.dto.request.AddFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.RetrieveFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.request.UpdateFaqRequest
import com.depromeet.threedollar.admin.service.faq.dto.response.FaqResponse
import com.depromeet.threedollar.common.exception.NotFoundException
import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqCategory
import com.depromeet.threedollar.domain.domain.faq.FaqCreator
import com.depromeet.threedollar.domain.domain.faq.FaqRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
    fun 새로운_FAQ_를_등록하면_FAQ_데이터가_추가된다() {
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

    @Test
    fun 등록된_FAQ를_수정하면_FAQ_데이터가_수정된다() {
        // give
        val question = "질문"
        val answer = "답변"
        val category = FaqCategory.CATEGORY

        val faq = FaqCreator.create("question", "answer", FaqCategory.CATEGORY)
        faqRepository.save(faq)

        val request = UpdateFaqRequest(question, answer, category)

        // when
        faqService.updateFaq(faq.id, request)

        // then
        val faqs = faqRepository.findAll()
        assertThat(faqs).hasSize(1)
        assertFaq(faqs[0], question, answer, category)
    }

    @Test
    fun 등록된_FAQ를_수정할때_해당하는_FAQ가_없으면_NotFOUND_EXCEPTION() {
        // given
        val request = UpdateFaqRequest("question", "answer", FaqCategory.CATEGORY)

        // when & then
        assertThatThrownBy { faqService.updateFaq(999L, request) }.isInstanceOf(NotFoundException::class.java)
    }

    @Test
    fun 특정_FAQ_를_삭제하면_해당_데이터가_삭제된다() {
        // given
        val faq = FaqCreator.create("question", "answer", FaqCategory.CATEGORY)
        faqRepository.save(faq)

        // when
        faqService.deleteFaq(faq.id)

        // then
        val faqs = faqRepository.findAll()
        assertThat(faqs).isEmpty()
    }

    @Test
    fun 특정_FAQ_를_삭제시_해당_FAQ가_없으면_NOTFOUND_EXCEPTION() {
        // when & then
        assertThatThrownBy { faqService.deleteFaq(999L) }.isInstanceOf(NotFoundException::class.java)
    }

    @Test
    fun 등록된_FAQ들을_카테고리_순서별로_조회한다() {
        // given
        val faq1 = FaqCreator.create("question1", "answer1", FaqCategory.CATEGORY)
        val faq2 = FaqCreator.create("question2", "answer2", FaqCategory.WITHDRAWAL)
        val faq3 = FaqCreator.create("question3", "answer3", FaqCategory.STORE)
        faqRepository.saveAll(listOf(faq1, faq2, faq3))

        // when
        val faqs = faqService.retrieveFaqs(RetrieveFaqRequest(null))

        // then
        assertThat(faqs).hasSize(3)
        assertFaqInfoResponse(faqs[0], faq3.id, faq3.question, faq3.answer, faq3.category)
        assertFaqInfoResponse(faqs[1], faq2.id, faq2.question, faq2.answer, faq2.category)
        assertFaqInfoResponse(faqs[2], faq1.id, faq1.question, faq1.answer, faq1.category)
    }

    private fun assertFaqInfoResponse(
        response: FaqResponse,
        faqId: Long,
        question: String,
        answer: String,
        category: FaqCategory
    ) {
        assertThat(response.faqId).isEqualTo(faqId)
        assertThat(response.question).isEqualTo(question)
        assertThat(response.answer).isEqualTo(answer)
        assertThat(response.category).isEqualTo(category)
    }

    private fun assertFaq(faq: Faq, question: String, answer: String, category: FaqCategory) {
        assertThat(faq.question).isEqualTo(question)
        assertThat(faq.answer).isEqualTo(answer)
        assertThat(faq.category).isEqualTo(category)
    }

}
