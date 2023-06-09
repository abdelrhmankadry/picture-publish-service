package com.kadry.picturePublishingService.repositories;

import com.kadry.picturePublishingService.domain.picture.Picture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends CrudRepository<Picture, Long> {

    @Query("SELECT p "+
    "FROM Picture p "+
    "WHERE p.state = 'ACCEPTED'")
    Optional<List<Picture>> getAllAcceptedPictures();

    Optional<Picture> findByUuid(UUID uuid);
}
