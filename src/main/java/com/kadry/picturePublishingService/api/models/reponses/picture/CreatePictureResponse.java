package com.kadry.picturePublishingService.api.models.reponses.picture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreatePictureResponse(
        @JsonProperty String uuid
) {
}
