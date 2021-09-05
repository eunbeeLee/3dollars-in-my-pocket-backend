package com.depromeet.threedollar.admin.controller

import com.depromeet.threedollar.application.common.dto.ApiResponse
import com.depromeet.threedollar.domain.domain.user.UserRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@AutoConfigureMockMvc
@SpringBootTest
abstract class ControllerTestUtils {

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected lateinit var token: String

    protected fun setup() {
        val response = objectMapper.readValue(
            mockMvc.perform(get("/test-token"))
                .andReturn()
                .response
                .contentAsString, object : TypeReference<ApiResponse<String>>() {}
        )
        token = response.data
        println(token)
    }

    protected fun cleanup() {
        userRepository.deleteAll()
    }

}
