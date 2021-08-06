package com.depromeet.threedollar.api;

import com.depromeet.threedollar.application.ThreeDollarApplicationRoot;
import com.depromeet.threedollar.domain.ThreeDollarDomainRoot;
import com.depromeet.threedollar.external.ThreeDollarExternalRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
    ThreeDollarApiApplication.class,
    ThreeDollarApplicationRoot.class,
    ThreeDollarDomainRoot.class,
    ThreeDollarExternalRoot.class
})
public class ThreeDollarApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreeDollarApiApplication.class, args);
    }

}
