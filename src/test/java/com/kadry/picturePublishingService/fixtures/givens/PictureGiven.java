package com.kadry.picturePublishingService.fixtures.givens;

import com.kadry.picturePublishingService.domain.picture.State;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import lombok.RequiredArgsConstructor;

import static com.kadry.picturePublishingService.fixtures.builders.domain.PictureBuilder.aPicture;

@RequiredArgsConstructor
public class PictureGiven {

    private final PictureRepository repository;
    public void hasManyPictures(){
        repository.save(aPicture().withState(State.ACCEPTED).build());
        repository.save(aPicture().withState(State.ACCEPTED).build());
        repository.save(aPicture().withState(State.PENDING).build());
    }
}
