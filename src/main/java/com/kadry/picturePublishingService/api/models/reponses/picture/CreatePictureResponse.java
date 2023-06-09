package com.kadry.picturePublishingService.api.models.reponses.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePictureResponse(
        @JsonProperty String uuid
) {
}
