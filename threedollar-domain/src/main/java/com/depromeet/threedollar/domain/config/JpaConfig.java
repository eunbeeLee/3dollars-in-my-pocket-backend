package com.depromeet.threedollar.domain.config;

import com.depromeet.threedollar.domain.ThreeDollarDomainRoot;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {ThreeDollarDomainRoot.class})
@EnableJpaRepositories(basePackageClasses = {ThreeDollarDomainRoot.class})
@EnableJpaAuditing
@Configuration
public class JpaConfig {

}