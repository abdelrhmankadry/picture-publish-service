package com.kadry.picturePublishingService.fixtures.expectations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kadry.picturePublishingService.api.models.reponses.picture.CreatePictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;

import com.kadry.picturePublishingService.domain.picture.Picture;
import com.kadry.picturePublishingService.domain.picture.State;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.UUID;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class PictureExpectation {

    private final PictureRepository pictureRepository;

    public PictureExpectation(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public static void resultContainsListOfPictures(MvcResult result){
        try {
            String content = result.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            PicturesResponse pictures = objectMapper.readValue(content, PicturesResponse.class);
            assertThat(pictures.pictures().size(), greaterThan(0));

        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    public static void resultContainsPictureWithId(MvcResult result, UUID uuid){
        try {
            String content = result.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            PictureResponse picture = objectMapper.readValue(content, PictureResponse.class);
            assertThat(picture.uuid(), is(uuid.toString()));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            fail(e.getMessage());
        }

    }
    public void hasPictureWithSameUuidReturnedFromRequest(MvcResult result){
        try {
            String content = result.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            CreatePictureResponse response = objectMapper.readValue(content, CreatePictureResponse.class);
           assertTrue(pictureRepository.findByUuid(UUID.fromString(response.uuid())).isPresent());
           assertEquals("Created picture should be in pending state",
                   pictureRepository.findByUuid(UUID.fromString(response.uuid())).orElse(new Picture()).getState()
                   , State.PENDING);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    public void pictureStatusHasBeenUpdated(UUID id, State state) {
        Picture picture = pictureRepository.findByUuid(id).orElse(new Picture());
        assertEquals("Picture state should be updated to Accepted", state, picture.getState());
    }
}
