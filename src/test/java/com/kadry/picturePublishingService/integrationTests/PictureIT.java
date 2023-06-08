package com.kadry.picturePublishingService.integrationTests;

import com.kadry.picturePublishingService.repositories.PictureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.kadry.picturePublishingService.fixtures.Given.givenThat;
import static com.kadry.picturePublishingService.fixtures.Expectation.expectThatResultContainsAListOfPictures;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
