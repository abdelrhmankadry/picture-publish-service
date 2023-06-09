package com.kadry.picturePublishingService.api.controllers;

import com.kadry.picturePublishingService.api.models.reponses.picture.CreatePictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;
import com.kadry.picturePublishingService.api.models.requests.PictureRequest;
import com.kadry.picturePublishingService.core.exceptions.PictureNotFound;
import com.kadry.picturePublishingService.core.security.AuthenticatedUser;

import com.kadry.picturePublishingService.domain.user.Role;
import com.kadry.picturePublishingService.services.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;
    @GetMapping("/pictures")
    public PicturesResponse getAcceptedPicture(){
        return pictureService.getAllAcceptedPictures();
    }

    @GetMapping("/picture/{id}")
    public PictureResponse getPictureById(@PathVariable String id, Authentication authentication) throws PictureNotFound {
        if(authentication == null){
            return pictureService.getAcceptedPictureById(id,null);
        }
        return pictureService.getAcceptedPictureById(id,
                new AuthenticatedUser((String) authentication.getCredentials(),
                        authentication.getAuthorities().stream().
                                map(author -> Role.valueOf(author.toString())).toList()));
    }

    @PostMapping("/picture")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePictureResponse uploadPicture(@RequestBody PictureRequest request){
       return pictureService.createPicture(request);
    }

    @GetMapping("/pending-pictures")
    public PicturesResponse getAllPendingPictures(){
        return  pictureService.getAllPendingPictures();
    }
}
