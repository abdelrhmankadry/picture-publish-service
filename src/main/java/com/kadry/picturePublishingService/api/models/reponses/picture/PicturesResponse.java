package com.kadry.picturePublishingService.api.models.reponses.picture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PicturesResponse(
        @JsonProperty List<PictureResponse> pictures
) {}
