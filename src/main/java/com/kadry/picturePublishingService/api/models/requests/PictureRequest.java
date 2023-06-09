package com.kadry.picturePublishingService.api.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kadry.picturePublishingService.domain.picture.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PictureRequest(
        @JsonProperty @NotBlank String description,
        @JsonProperty Category category,
        @JsonProperty @NotNull byte[] picture
) {}
