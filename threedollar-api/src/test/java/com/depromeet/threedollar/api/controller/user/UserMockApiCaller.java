package com.depromeet.threedollar.api.controller.user;

import com.depromeet.threedollar.api.common.dto.ApiResponse;
import com.depromeet.threedollar.api.controller.MockMvcUtils;
import com.depromeet.threedollar.api.service.user.dto.request.CheckDuplicateNameRequest;
import com.depromeet.threedollar.api.service.user.dto.request.UpdateUserInfoRequest;
import com.depromeet.threedollar.api.service.user.dto.response.UserInfoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserMockApiCaller extends MockMvcUtils {

    public UserMockApiCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public ApiResponse<String> getTestToken() throws Exception {
        MockHttpServletRequestBuilder builder = get("/test-token");

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<UserInfoResponse> getMyUserInfo(String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, token);

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<UserInfoResponse> updateMyUserInfo(UpdateUserInfoRequest request, String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = put("/api/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request));

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<String> checkAvailableName(CheckDuplicateNameRequest request, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v2/user/name/check")
            .param("name", request.getName());
        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

}
