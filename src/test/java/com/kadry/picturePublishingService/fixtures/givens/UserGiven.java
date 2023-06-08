package com.kadry.picturePublishingService.fixtures.givens;

import com.kadry.picturePublishingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.kadry.picturePublishingService.fixtures.builders.domain.UserBuilder.anUser;

@RequiredArgsConstructor
public class UserGiven {

    private final UserRepository userRepository;

    public void hasUser(String email, String Password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRepository.save(anUser()
                .withEmail(email)
                .withPassword(passwordEncoder.encode(Password))
                .build());
    }
}
