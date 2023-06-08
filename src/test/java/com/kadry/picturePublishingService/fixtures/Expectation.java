package com.kadry.picturePublishingService.fixtures;

import com.kadry.picturePublishingService.fixtures.expectations.PictureExpectation;
import org.springframework.test.web.servlet.MvcResult;

public class Expectation {

    public static void expectThatResultContainsAListOfPictures(MvcResult result){
        PictureExpectation.resultContainsListOfPictures(result);
    }
}
