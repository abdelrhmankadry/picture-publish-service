package com.kadry.picturePublishingService.services;

import com.kadry.picturePublishingService.api.models.reponses.authentication.ResponseCode;
import com.kadry.picturePublishingService.api.models.reponses.authentication.SignUpResponse;
import com.kadry.picturePublishingService.api.models.requests.CredentialsRequest;
import com.kadry.picturePublishingService.domain.user.Role;
import com.kadry.picturePublishingService.domain.user.User;
import com.kadry.picturePublishingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public SignUpResponse createUser(CredentialsRequest credentialsRequest){
        if(userRepository.existsByEmail(credentialsRequest.email())){
            return new SignUpResponse(ResponseCode.EMAIL_EXISTS, "Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(credentialsRequest.password());

        userRepository.save(new User(credentialsRequest.email(), encodedPassword, Role.ROLE_USER));
        return new SignUpResponse(ResponseCode.SIGNUP_SUCCEEDED, "User created");
    }
}
