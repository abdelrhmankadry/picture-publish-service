package com.kadry.picturePublishingService.integrationTests;

import com.kadry.picturePublishingService.domain.picture.State;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.UUID;

import static com.kadry.picturePublishingService.fixtures.Expectation.*;
import static com.kadry.picturePublishingService.fixtures.Given.givenThat;
import static com.kadry.picturePublishingService.fixtures.builders.requests.PictureRequestBuilder.aPictureRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PictureIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PictureRepository pictureRepository;
    @Test
    @Transactional
    public void getAllPicturesTest() throws Exception {

        givenThat(pictureRepository).hasManyPictures();
        MvcResult result= mockMvc.perform(get("/api/pictures"))
              .andExpect(status().isOk()).andReturn();
        expectThatResultContainsAListOfPictures(result);
    }

    @Test
    @Transactional
    public void getPictureByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        givenThat(pictureRepository).hasAPictureWithId(id);
         mockMvc.perform(get("/api/picture/"+id))
             .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    @Transactional
    public void getPendingPictureWithUserRoleTest() throws Exception {
        UUID id = UUID.randomUUID();
        givenThat(pictureRepository).hasAPendingPictureWithId(id);
        mockMvc.perform(get("/api/picture/"+id))
            .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    @WithMockUser
    @Transactional
    public void uploadPictureTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/api/picture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aPictureRequest().json()))
                .andExpect(status().isCreated())
                .andReturn();
        expectThat(pictureRepository).hasPictureWithSameUuidReturnedFromRequest(result);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Transactional
    public void getAllPendingPictures() throws Exception {
        givenThat(pictureRepository).hasManyPendingPictures();
        MvcResult result= mockMvc.perform(get("/api/pending-pictures"))
               .andExpect(status().isOk()).andReturn();
        expectThatResultContainsAListOfPictures(result);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Transactional
    public void acceptPendingPictureTest() throws Exception {
        UUID id = UUID.randomUUID();
        givenThat(pictureRepository).hasAPendingPictureWithId(id);

        mockMvc.perform( patch("/api/accept-picture/"+id))
                .andExpect(status().isOk())
                .andReturn();

        expectThat(pictureRepository).pictureStatusHasBeenUpdated(id, State.ACCEPTED);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Transactional
    public void rejectPendingPictureTest() throws Exception {
        UUID id = UUID.randomUUID();
        givenThat(pictureRepository).hasAPendingPictureWithId(id);

        mockMvc.perform( patch("/api/reject-picture/"+id))
                .andExpect(status().isOk())
                .andReturn();

        expectThat(pictureRepository).pictureStatusHasBeenUpdated(id, State.REJECTED);
    }
}
