package com.depromeet.threedollar.api.controller;

import com.depromeet.threedollar.api.controller.user.api.UserMockApiCaller;
import com.depromeet.threedollar.domain.domain.user.User;
import com.depromeet.threedollar.domain.domain.user.UserRepository;
import com.depromeet.threedollar.domain.domain.user.UserSocialType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    protected UserMockApiCaller userMockApiCaller;

    protected User testUser;

    protected String token;

    protected void setup() throws Exception {
        userMockApiCaller = new UserMockApiCaller(mockMvc, objectMapper);
        token = userMockApiCaller.getTestToken().getData();
        testUser = userRepository.findUserBySocialIdAndSocialType("test-uid", UserSocialType.KAKAO);
    }

    protected void cleanup() {
        userRepository.deleteAll();
    }

}
