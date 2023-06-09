package com.kadry.picturePublishingService.fixtures;

import com.kadry.picturePublishingService.fixtures.expectations.PictureExpectation;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

public class Expectation {

    public static void expectThatResultContainsAListOfPictures(MvcResult result){
        PictureExpectation.resultContainsListOfPictures(result);
    }

    public static void expectThatResultContainsPictureWithId(MvcResult result, UUID uuid){
        PictureExpectation.resultContainsPictureWithId(result, uuid);
    }

    public static PictureExpectation expectThat(PictureRepository pictureRepository){
        return new PictureExpectation(pictureRepository);
    }
}
