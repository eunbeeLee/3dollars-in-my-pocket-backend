package com.depromeet.threedollar.admin.service.faq

import com.depromeet.threedollar.common.exception.ErrorCode.*
import com.depromeet.threedollar.common.exception.NotFoundException
import com.depromeet.threedollar.domain.domain.faq.Faq
import com.depromeet.threedollar.domain.domain.faq.FaqRepository

object FaqAdminServiceUtils {

    fun findFaqById(faqRepository: FaqRepository, faqId: Long): Faq {
        return faqRepository.findFaqById(faqId)
            ?: throw NotFoundException("해당하는 Faq ($faqId)를 찾을 수 없습니다", NOT_FOUND_FAQ_EXCEPTION)
    }

}
