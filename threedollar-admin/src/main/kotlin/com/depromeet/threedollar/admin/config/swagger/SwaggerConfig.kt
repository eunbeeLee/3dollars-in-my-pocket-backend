package com.depromeet.threedollar.admin.config.swagger

import com.depromeet.threedollar.admin.config.resolver.AdminId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.bind.annotation.RestController
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Import(BeanValidatorPluginsConfiguration::class)
@EnableSwagger2
@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(creteApiInfo())
            .ignoredParameterTypes(AdminId::class.java)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
            .paths(PathSelectors.ant("/admin/**"))
            .build()
    }

    private fun creteApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("가슴속 삼천원 관리자 API")
            .description("가슴속 삼천원 관리자 API Docs")
            .build()
    }

}
