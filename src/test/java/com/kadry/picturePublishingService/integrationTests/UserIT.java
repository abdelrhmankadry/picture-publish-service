package com.kadry.picturePublishingService.integrationTests;


import com.kadry.picturePublishingService.fixtures.builders.requests.SignUpRequestBuilder;
import com.kadry.picturePublishingService.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.kadry.picturePublishingService.fixtures.builders.requests.SignUpRequestBuilder.aSignUpRequest;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserIT {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void signUpTest() throws Exception {

        mockMvc.perform(post("/api/signup")
            .contentType(MediaType.APPLICATION_JSON)
                .content(aSignUpRequest().json()))
                .andExpect(status().isCreated());

        assertTrue("User should be signed up, and should exist in the database.",
                userRepository.findUserByEmail(SignUpRequestBuilder.EMAIL).isPresent());

    }
}
