package com.kadry.picturePublishingService.api.models.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SignInResponse(
        @JsonProperty @NotBlank String accessToken
) {}
