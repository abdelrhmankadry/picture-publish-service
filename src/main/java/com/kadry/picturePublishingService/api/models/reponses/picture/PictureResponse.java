package com.kadry.picturePublishingService.api.models.reponses.picture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kadry.picturePublishingService.domain.picture.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@JsonIgnoreProperties(ignoreUnknown = true)
public record PictureResponse(
        @JsonProperty @NotBlank String uuid,
        @JsonProperty @NotBlank String Description,
        @JsonProperty Category category,
        @JsonProperty @NotNull String picture
) { }
