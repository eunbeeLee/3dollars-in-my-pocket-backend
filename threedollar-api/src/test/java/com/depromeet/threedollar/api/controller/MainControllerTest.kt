package com.depromeet.threedollar.api.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@AutoConfigureMockMvc
@SpringBootTest
class MainControllerTest(
    @Autowired
    private val mockMvc: MockMvc
) {

    @Test
    fun ping() {
        this.mockMvc.perform(get("/ping"))
            .andExpect(status().isOk)
    }

    @Test
    fun checkVersion_BadReqeust_without_UserAgent() {
        this.mockMvc.perform(
            get("/version")
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun checkVersion_OK_with_UserAgent() {
        this.mockMvc.perform(
            get("/version")
                .header("User-Agent", "OK")
        )
            .andExpect(status().isOk)
    }

}
