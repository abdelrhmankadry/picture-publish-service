package com.kadry.picturePublishingService.api.models.reponses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignUpResponse(
        @JsonProperty ResponseCodes code,
        @JsonProperty String message
) {
}
