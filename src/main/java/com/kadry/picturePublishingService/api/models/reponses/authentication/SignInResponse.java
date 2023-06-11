package com.kadry.picturePublishingService.api.models.reponses.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SignInResponse(
        @JsonProperty @NotBlank String accessToken
) {}
