package com.kadry.picturePublishingService.services;

import com.kadry.picturePublishingService.api.models.reponses.picture.CreatePictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;
import com.kadry.picturePublishingService.api.models.requests.PictureRequest;
import com.kadry.picturePublishingService.core.exceptions.PictureNotFoundException;
import com.kadry.picturePublishingService.core.security.AuthenticatedUser;
import com.kadry.picturePublishingService.domain.picture.Picture;
import com.kadry.picturePublishingService.domain.picture.State;
import com.kadry.picturePublishingService.domain.user.Role;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;


    @Transactional
    public PicturesResponse getAllAcceptedPictures(){
        return  this.getPicturesByState(State.ACCEPTED);
    }

    @Transactional
    public PicturesResponse getAllPendingPictures() {
        return  this.getPicturesByState(State.PENDING);
    }

    public byte[] getAcceptedPictureById(String pictureId) throws PictureNotFoundException {
        Picture picture = pictureRepository.findByUuid(UUID.fromString(pictureId))
                .orElseThrow(PictureNotFoundException::new);
        if(picture.getState() == State.PENDING || picture.getState() == State.REJECTED)
                throw new AccessDeniedException("Only admins can preview non accepted data");
        return picture.getPictureData();
    }

    public byte[] getPendingPictureById(String pictureId) throws PictureNotFoundException {
        Picture picture = pictureRepository.findByUuid(UUID.fromString(pictureId))
                .orElseThrow(PictureNotFoundException::new);
        return picture.getPictureData();
    }

    public CreatePictureResponse createPicture(PictureRequest request) {
        int sizeInBytes = Base64.getDecoder().decode(request.picture()).length;
        double sizeInMB = (double) sizeInBytes / (1024 * 1024);
        if(sizeInMB> 2.0){
            throw new IllegalArgumentException("Picture size should be less than 2MB");
        }
        Picture picture = new Picture(UUID.randomUUID(), request.description(),
                request.category(), State.PENDING, Base64.getDecoder().decode(request.picture()));
        pictureRepository.save(picture);
        return new CreatePictureResponse(picture.getUuid().toString());
    }



    private PicturesResponse getPicturesByState(State state){

        String BASE_URL = state == State.ACCEPTED? "http://localhost:8080/api/picture/"
                : "http://localhost:8080/api/pending-picture/";

        List<PictureResponse> listOfPictureResponses = pictureRepository.getPicturesByState(state)
                .orElse(new ArrayList<>()).stream()
                .map(picture -> new PictureResponse(picture.getUuid().toString() ,picture.getDescription(), picture.getCategory(),BASE_URL + picture.getUuid()))
                .toList();
        System.out.println(listOfPictureResponses);
        return new PicturesResponse(listOfPictureResponses);
    }

    public void acceptPicture(String id) throws PictureNotFoundException {
        Picture picture = pictureRepository.findByUuid(UUID.fromString(id))
              .orElseThrow(PictureNotFoundException::new);
        picture.setState(State.ACCEPTED);
        pictureRepository.save(picture);
    }

    public void rejectPicture(String id) throws PictureNotFoundException {
        Picture picture = pictureRepository.findByUuid(UUID.fromString(id))
                .orElseThrow(PictureNotFoundException::new);
        picture.setState(State.REJECTED);
        picture.setPictureData(new byte[]{});
        pictureRepository.save(picture);
    }
}
