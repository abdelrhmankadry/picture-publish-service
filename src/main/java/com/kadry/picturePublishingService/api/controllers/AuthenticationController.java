package com.kadry.picturePublishingService.api.controllers;

import com.kadry.picturePublishingService.api.models.reponses.SignUpResponse;
import com.kadry.picturePublishingService.api.models.requests.SignUpRequest;
import com.kadry.picturePublishingService.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signup(@RequestBody @Valid SignUpRequest signUpRequest){
        return this.userService.createUser(signUpRequest);
    }
}
