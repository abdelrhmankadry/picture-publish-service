package com.kadry.picturePublishingService.services;

import com.kadry.picturePublishingService.api.models.reponses.ResponseCodes;
import com.kadry.picturePublishingService.api.models.reponses.SignUpResponse;
import com.kadry.picturePublishingService.api.models.requests.SignUpRequest;
import com.kadry.picturePublishingService.domain.Role;
import com.kadry.picturePublishingService.domain.User;
import com.kadry.picturePublishingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public SignUpResponse createUser(SignUpRequest signUpRequest){
        if(userRepository.existsByEmail(signUpRequest.email())){
            return new SignUpResponse(ResponseCodes.SIGNUP_FAILED, "Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        userRepository.save(new User(signUpRequest.email(), encodedPassword, Role.USER));
        return new SignUpResponse(ResponseCodes.SIGNUP_SUCCEEDED, "User created");
    }
}
