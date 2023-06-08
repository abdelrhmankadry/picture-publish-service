package com.kadry.picturePublishingService.fixtures.expectations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;

import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class PictureExpectation {

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
}
