package com.kadry.picturePublishingService.api.controllers;

import com.kadry.picturePublishingService.api.models.reponses.authentication.ResponseCode;
import com.kadry.picturePublishingService.api.models.reponses.authentication.SignInResponse;
import com.kadry.picturePublishingService.api.models.reponses.authentication.SignUpResponse;
import com.kadry.picturePublishingService.api.models.requests.CredentialsRequest;
import com.kadry.picturePublishingService.core.security.Jwt;
import com.kadry.picturePublishingService.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final Jwt jwtProvider;
    @PostMapping(value = "/signup")
    @Operation(summary = "User signup", description = "Registers a new user.")
    public ResponseEntity<SignUpResponse>  signup(@RequestBody @Valid CredentialsRequest signUpRequest){
        SignUpResponse  response= this.userService.createUser(signUpRequest);
        if (response.code()== ResponseCode.EMAIL_EXISTS){
            return  ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(value = "/signin")
    @Operation(summary = "User signup", description = "Registers a new user.")
    public SignInResponse signin(@RequestBody @Valid CredentialsRequest signInRequest){
        var authenticate = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                ));

        return new SignInResponse(jwtProvider.generateToken(authenticate));

    }
}
