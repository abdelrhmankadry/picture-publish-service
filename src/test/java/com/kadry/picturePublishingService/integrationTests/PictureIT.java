package com.kadry.picturePublishingService.integrationTests;

import com.kadry.picturePublishingService.repositories.PictureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static com.kadry.picturePublishingService.fixtures.Expectation.*;
import static com.kadry.picturePublishingService.fixtures.Given.givenThat;
import static com.kadry.picturePublishingService.fixtures.builders.requests.PictureRequestBuilder.aPictureRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PictureIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PictureRepository pictureRepository;
    @Test
    public void getAllPicturesTest() throws Exception {

        givenThat(pictureRepository).hasManyPictures();
        MvcResult result= mockMvc.perform(get("/api/pictures"))
              .andExpect(status().isOk()).andReturn();
        expectThatResultContainsAListOfPictures(result);
    }

    @Test
    public void getPictureByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        givenThat(pictureRepository).hasAPictureWithId(id);
        MvcResult result= mockMvc.perform(get("/api/picture/"+id))
             .andExpect(status().isOk()).andReturn();
        expectThatResultContainsPictureWithId(result, id);

    }

    @Test
    @WithMockUser
    public void getPendingPictureWithUserRoleTest() throws Exception {
        UUID id = UUID.randomUUID();
        givenThat(pictureRepository).hasAPendingPictureWithId(id);
        mockMvc.perform(get("/api/picture/"+id))
            .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    @WithMockUser
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
    public void getAllPendingPictures() throws Exception {
        givenThat(pictureRepository).hasManyPendingPictures();
        MvcResult result= mockMvc.perform(get("/api/pending-pictures"))
               .andExpect(status().isOk()).andReturn();
        expectThatResultContainsAListOfPictures(result);
    }
}
