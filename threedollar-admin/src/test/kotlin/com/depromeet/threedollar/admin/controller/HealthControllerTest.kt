package com.depromeet.threedollar.admin.controller

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@SpringBootTest
class HealthControllerTest(
    private val mockMvc: MockMvc
) {

    @Test
    fun healthCheck() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/ping"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

}
