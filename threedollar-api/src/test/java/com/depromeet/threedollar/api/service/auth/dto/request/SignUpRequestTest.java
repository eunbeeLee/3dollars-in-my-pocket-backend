package com.depromeet.threedollar.api.service.auth.dto.request;

import com.depromeet.threedollar.api.service.user.dto.request.CheckAvailableNameRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SignUpRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @ValueSource(strings = {"디프만", "강승호", "승호-강", "will"})
    void 사용가능한_이름이면_유효성_검사를_통과한다(String name) {
        // given
        CheckAvailableNameRequest request = CheckAvailableNameRequest.testInstance(name);

        // when
        Set<ConstraintViolation<CheckAvailableNameRequest>> constraintViolations = validator.validate(request);

        // then
        assertThat(constraintViolations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-a-", "a--", "디", "디#프만", "디+프만"})
    void 사용가능하지_않은_이름이면_유효성_검사에서_실패한다(String name) {
        // given
        CheckAvailableNameRequest request = CheckAvailableNameRequest.testInstance(name);

        // when
        Set<ConstraintViolation<CheckAvailableNameRequest>> constraintViolations = validator.validate(request);

        // then
        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).hasSize(1);
    }

}
