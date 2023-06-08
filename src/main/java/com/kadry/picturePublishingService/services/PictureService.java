package com.kadry.picturePublishingService.services;

import com.kadry.picturePublishingService.api.models.reponses.picture.PictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;
import com.kadry.picturePublishingService.domain.picture.Picture;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    public PicturesResponse getAllAcceptedPictures(){

        List<PictureResponse> listOfPictureResponses = pictureRepository.getAllAcceptedPictures()
                .orElse(new ArrayList<>()).stream()
                .map(picture -> new PictureResponse(picture.getDescription(), picture.getCategory(), picture.getPictureData()))
                .toList();
        return new PicturesResponse(listOfPictureResponses);
    }
}
