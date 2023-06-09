package com.kadry.picturePublishingService.services;

import com.kadry.picturePublishingService.api.models.reponses.picture.CreatePictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;
import com.kadry.picturePublishingService.api.models.requests.PictureRequest;
import com.kadry.picturePublishingService.core.exceptions.PictureNotFound;
import com.kadry.picturePublishingService.core.security.AuthenticatedUser;
import com.kadry.picturePublishingService.domain.picture.Picture;
import com.kadry.picturePublishingService.domain.picture.State;
import com.kadry.picturePublishingService.domain.user.Role;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    public PicturesResponse getAllAcceptedPictures(){

        List<PictureResponse> listOfPictureResponses = pictureRepository.getAllAcceptedPictures()
                .orElse(new ArrayList<>()).stream()
                .map(picture -> new PictureResponse(picture.getUuid().toString() ,picture.getDescription(), picture.getCategory(), picture.getPictureData()))
                .toList();
        return new PicturesResponse(listOfPictureResponses);
    }

    public PictureResponse getAcceptedPictureById(String pictureId, AuthenticatedUser authentication) throws PictureNotFound {
        Picture picture = pictureRepository.findByUuid(UUID.fromString(pictureId))
                .orElseThrow(PictureNotFound::new);
        if(picture.getState() == State.PENDING || picture.getState() == State.REJECTED)
            if(authentication == null || !authentication.roles().contains(Role.ROLE_ADMIN))
                throw new AccessDeniedException("Only admins can preview non accepted data");
        return new PictureResponse(picture.getUuid().toString(),picture.getDescription(),
                picture.getCategory(), picture.getPictureData());
    }

    public CreatePictureResponse createPicture(PictureRequest request) {
        Picture picture = new Picture(UUID.randomUUID(), request.description(),
                request.category(), State.PENDING, request.picture());
        pictureRepository.save(picture);
        return new CreatePictureResponse(picture.getUuid().toString());
    }
}
