package com.kadry.picturePublishingService.integrationTests;


import com.kadry.picturePublishingService.api.models.reponses.authentication.ResponseCode;
import com.kadry.picturePublishingService.fixtures.builders.requests.CredentialsRequestBuilder;
import com.kadry.picturePublishingService.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.kadry.picturePublishingService.fixtures.Given.givenThat;
import static com.kadry.picturePublishingService.fixtures.builders.requests.CredentialsRequestBuilder.aSignInRequest;
import static com.kadry.picturePublishingService.fixtures.builders.requests.CredentialsRequestBuilder.aSignUpRequest;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIT {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    public void signUpTest() throws Exception {

        mockMvc.perform(post("/api/signup")
            .contentType(MediaType.APPLICATION_JSON)
                .content(aSignUpRequest().json()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(ResponseCode.SIGNUP_SUCCEEDED.toString()));

        assertTrue("User should be signed up, and should exist in the database.",
                userRepository.findUserByEmail(CredentialsRequestBuilder.EMAIL).isPresent());

    }

    @Test
    @Transactional
    public void signUpFailsTest() throws Exception {
        givenThat(userRepository).hasUser(CredentialsRequestBuilder.EMAIL,
                CredentialsRequestBuilder.PASSWORD);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aSignUpRequest()
                                .withEmail(CredentialsRequestBuilder.EMAIL).json()))
                .andExpect(jsonPath("$.code").value(ResponseCode.EMAIL_EXISTS.toString()));

    }

    @Test
    @Transactional
    public void signInTest() throws Exception {

        givenThat(userRepository).hasUser(CredentialsRequestBuilder.EMAIL,
                CredentialsRequestBuilder.PASSWORD);

     mockMvc.perform(post("/api/signin")
              .contentType(MediaType.APPLICATION_JSON)
              .content(aSignInRequest().json()))
              .andExpect(status().isOk())
             .andExpect(jsonPath("$.accessToken").isNotEmpty());


    }
}
