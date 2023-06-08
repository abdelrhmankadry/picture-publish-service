package com.kadry.picturePublishingService.fixtures;

import com.kadry.picturePublishingService.fixtures.givens.UserGiven;
import com.kadry.picturePublishingService.repositories.UserRepository;

public class Given {


    public static UserGiven givenThat(UserRepository userRepository){
        return new UserGiven(userRepository);
    }

}
