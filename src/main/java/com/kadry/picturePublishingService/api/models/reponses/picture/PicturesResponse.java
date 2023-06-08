package com.kadry.picturePublishingService.api.models.reponses.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PicturesResponse(
        @JsonProperty List<PictureResponse> pictures
) {}
