package com.depromeet.threedollar.api.config.validator

import org.springframework.stereotype.Component
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Component
class NickNameValidator : ConstraintValidator<NickName, String> {

    override fun isValid(nickName: String, context: ConstraintValidatorContext): Boolean {
        return NICKNAME_REGEX.matcher(nickName).matches()
    }

    /**
     * 닉네임 규칙
     * [한글, 영대소문자, 숫자]로 시작 및 종료해야하며, ['한글', '영대소문자', '숫자', '-', '_']로 이루어진 2자 이상 20자 이하의 닉네임.
     */
    companion object {
        private val NICKNAME_REGEX = Pattern.compile("^[ㄱ-ㅎ가-힣a-zA-Z0-9][ㄱ-ㅎ가-힣a-zA-Z0-9_-]{0,18}[ㄱ-ㅎ가-힣a-zA-Z0-9]$")
    }

}
