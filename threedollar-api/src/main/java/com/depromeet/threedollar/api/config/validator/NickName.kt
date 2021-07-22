package com.depromeet.threedollar.api.config.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [NickNameValidator::class])
@Target(AnnotationTarget.FIELD)
annotation class NickName(

    val message: String = "잘못된 닉네임입니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []

)
