package com.kadry.picturePublishingService.api.models.reponses.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SignUpResponse(
        @JsonProperty ResponseCode code,
        @JsonProperty String message
) {
}
