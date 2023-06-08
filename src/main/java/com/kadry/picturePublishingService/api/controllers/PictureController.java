package com.kadry.picturePublishingService.api.controllers;

import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;
import com.kadry.picturePublishingService.services.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;
    @GetMapping("/pictures")
    public PicturesResponse getAcceptedPicture(){
        return pictureService.getAllAcceptedPictures();
    }
}
