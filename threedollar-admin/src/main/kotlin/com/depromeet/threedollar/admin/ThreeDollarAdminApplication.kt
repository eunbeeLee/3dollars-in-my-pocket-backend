package com.depromeet.threedollar.admin

import com.depromeet.threedollar.domain.ThreeDollarDomainRoot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackageClasses = [
        ThreeDollarAdminApplication::class,
        ThreeDollarDomainRoot::class
    ]
)
class ThreeDollarAdminApplication

fun main(args: Array<String>) {
    runApplication<ThreeDollarAdminApplication>(*args)
}
