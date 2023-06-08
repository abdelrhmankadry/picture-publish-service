package com.kadry.picturePublishingService.fixtures;

import com.kadry.picturePublishingService.fixtures.givens.PictureGiven;
import com.kadry.picturePublishingService.fixtures.givens.UserGiven;
import com.kadry.picturePublishingService.repositories.PictureRepository;
import com.kadry.picturePublishingService.repositories.UserRepository;

public class Given {


    public static UserGiven givenThat(UserRepository userRepository){
        return new UserGiven(userRepository);
    }
    public static PictureGiven givenThat(PictureRepository pictureRepository){ return new PictureGiven(pictureRepository);}

}
