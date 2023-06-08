package com.kadry.picturePublishingService.api.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SignUpRequest (
        @JsonProperty("email") @Email @NotBlank String email,
        @JsonProperty("password") @NotBlank @Size(min=8, max=56) String password
){}

