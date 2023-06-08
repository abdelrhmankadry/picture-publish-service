package com.kadry.picturePublishingService.api.controllers;

import com.kadry.picturePublishingService.api.models.reponses.SignInResponse;
import com.kadry.picturePublishingService.api.models.reponses.SignUpResponse;
import com.kadry.picturePublishingService.api.models.requests.CredentialsRequest;
import com.kadry.picturePublishingService.core.security.Jwt;
import com.kadry.picturePublishingService.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final Jwt jwtProvider;
    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signup(@RequestBody @Valid CredentialsRequest signUpRequest){
        return this.userService.createUser(signUpRequest);
    }

    @PostMapping(value = "/signin")
    public SignInResponse signin(@RequestBody @Valid CredentialsRequest signInRequest){
        var authenticate = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                ));

        return new SignInResponse(jwtProvider.generateToken(authenticate));
//        return new SignInResponse("dump");
    }
}
